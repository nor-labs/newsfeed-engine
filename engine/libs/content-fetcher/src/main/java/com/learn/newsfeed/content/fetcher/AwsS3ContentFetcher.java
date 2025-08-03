package com.learn.newsfeed.content.fetcher;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import java.nio.charset.StandardCharsets;
public class AwsS3ContentFetcher implements ContentFetcher{
    private final S3Client s3Client;

    public AwsS3ContentFetcher(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public byte[] getContentAsBytes(String bucket, String key) {
        var getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
        try (var response = s3Client.getObject(getObjectRequest)) {
            return response.readAllBytes();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getContentAsString(String bucket, String key) {
        var getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
        try (var response = s3Client.getObject(getObjectRequest)) {
            return new String(response.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
