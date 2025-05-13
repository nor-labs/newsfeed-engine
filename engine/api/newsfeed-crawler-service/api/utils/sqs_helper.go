package utils

import (
	"com.learn.newsfeed/newsfeed/api/config"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/sqs"
)

type LoaderMetadata struct {
	Timestamp string
	ObjectUrl string
	BucketName string
	MessageId string
}

func SendMessage(loaderMedatadata LoaderMetadata){
	sess := session.Must(session.NewSessionWithOptions(session.Options{
		SharedConfigState: session.SharedConfigEnable,
	}))
	sqsClient := sqs.New(sess)
	messageBody, err := json.Marshal(loaderMedatadata)

	if err != nil {
		 fmt.Printf("failed to marshal metadata: %w", err)
	}else
	{
		_,err := sqsClient.SendMessage(&sqs.SendMessageInput{
			QueueUrl: aws.String(config.CONFIGURATIONS.SQS.QueueUrl),
			MessageBody:aws.String(string(messageBody)) ,
		})

		if err != nil {
			fmt.Printf("SQS Error: ", err)
		}else{
			fmt.Println("SQS Message sent")
		}
	}
}