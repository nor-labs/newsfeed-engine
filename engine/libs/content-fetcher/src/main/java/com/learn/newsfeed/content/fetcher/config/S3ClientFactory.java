package com.learn.newsfeed.content.fetcher.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientFactory {

    public static S3Client createS3Client(){
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
