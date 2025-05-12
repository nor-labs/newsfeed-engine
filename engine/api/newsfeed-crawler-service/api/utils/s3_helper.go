package utils

import (
	"bytes"
	"context"

	"com.learn.newsfeed/newsfeed/api/model"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

func PutObject(ctx context.Context,bucket string, key string, data []byte)(model.S3Output){

	s3Config , err := config.LoadDefaultConfig(ctx,config.WithRegion("us-east-1"))

	if err != nil {
		return model.S3Output{ Error: err}
	}

	s3Client := s3.NewFromConfig(s3Config)

	putRequest := &s3.PutObjectInput{
		Bucket: aws.String(bucket),
		Key:aws.String(key),
		Body: bytes.NewReader(data),
	}

	_, err = s3Client.PutObject(ctx,putRequest)

	if err != nil {
		return model.S3Output{ Error: err}
	}

	return model.S3Output{ Success: "document uploaded successfully"}
}