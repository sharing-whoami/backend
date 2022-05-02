package whoami.core.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import whoami.core.domain.members.Members;
import whoami.core.domain.members.MembersRepository;
import whoami.core.dto.members.ProfileDeleteRequestDto;
import whoami.core.dto.members.ProfileUploadRequestDto;
import whoami.core.error.Response;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Service {
    private final AmazonS3Client amazonS3Client;
    private final MembersRepository membersRepository;
    private final Response response;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // NOTE : S3 버킷 이름

    @Transactional
    public ResponseEntity<? extends Object> profileUpload(ProfileUploadRequestDto requestDto,MultipartFile multipartFile){
        String userId=requestDto.getUserId();
        String dirName=requestDto.getDirName();
        Optional<Members> members=membersRepository.findByUserId(userId);

        if (userId==null || multipartFile==null){
            return response.fail("입력값이 비어있습니다.",HttpStatus.BAD_REQUEST);
        }
        if (members.isEmpty()){
            return response.fail("존재하지 않는 회원입니다.",HttpStatus.BAD_REQUEST);
        }

        try{
            if (members.get().getProfile()!=null){ // NOTE : 프로필 수정하기
                deleteS3(members.get().getProfile());
            }
            Optional<File> uploadFile = convert(multipartFile);  // 파일 변환할 수 없으면 에러
            if (uploadFile.isEmpty()){
                return response.fail("파일 변환이 올바르게 이루어지지 않았습니다.", HttpStatus.BAD_REQUEST);
            }
            String url = upload(uploadFile.get(), dirName,members.get());  // s3에 저장된 이미지의 주소 -> db에 넣기.
            return response.success(Collections.EMPTY_LIST,"프로필 사진 업로드가 완료되었습니다.",HttpStatus.CREATED);
        } catch (Exception e){
            return response.fail(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<? extends Object> profileDelete(ProfileDeleteRequestDto requestDto){
        Optional<Members> members=membersRepository.findByUserId(requestDto.getUserId());
        if (members.get().getProfile()==null){
            return response.fail("삭제할 사진이 없습니다.",HttpStatus.BAD_REQUEST);
        }
        try{
            deleteS3(members.get().getProfile());
            membersUpdateUrl(null,members.get());
            return response.success(Collections.EMPTY_LIST,"프로필 사진 삭제가 완료되었습니다.",HttpStatus.OK);
        }catch (Exception e){
            return response.fail(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    // NOTE : url -> 회원정보 DB에 넣기
    private void membersUpdateUrl(String url,Members members){
        members.update(url);
    }

    // NOTE : S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName,Members members) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        membersUpdateUrl(fileName,members);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // NOTE : S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // NOTE : 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // NOTE : 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private void deleteS3(String source) {
        amazonS3Client.deleteObject(bucket, source);
    }
}