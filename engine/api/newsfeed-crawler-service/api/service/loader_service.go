package service

import (
	"context"
	"encoding/xml"
	"fmt"
	"strings"
	"time"
	"com.learn.newsfeed/newsfeed/api/config"
	"com.learn.newsfeed/newsfeed/api/model"
	"com.learn.newsfeed/newsfeed/api/utils"
)
type LoaderResponse  struct {
	Success string
	Error string
}
func LoadContent(ctx context.Context)(LoaderResponse){

	for _, source := range config.CONFIGURATIONS.Sources{

		sourceUri := config.CONFIGURATIONS.Loader.ResourceUriPrefix + source +".xml"
		content, err :=utils.GetContent(sourceUri)

		if (err != nil){
			return LoaderResponse{ Error: "Error fetching content:"+ err.Error()}
		}
	
		var rss model.RSS
		err = xml.Unmarshal([]byte(content), &rss)
	
		if err != nil {
			return LoaderResponse{ Error: "error marshalling NewsFeed to XML:"+ err.Error()}
	   }
	
		newsFeed := utils.MapNewsFeed(rss)
	
		xmlContent, err := xml.MarshalIndent(newsFeed, "", "  ")
		utils.PutObject(ctx,"2025-news-feed",getFileKey(source),xmlContent)
	
		if err != nil {
			 return LoaderResponse{ Error: "error marshalling NewsFeed to XML:"+ err.Error()}
		}
	}



	return LoaderResponse{ Success: "Loaded content to s3"}
}


func getFileKey(source string)(string){
	formatted := fmt.Sprintf(
		"%02d-%s-%d-%02d_%02d_%02d",
		time.Now().Day(),
		strings.ToLower(time.Now().Month().String()),
		time.Now().Year(),
		time.Now().Hour(),
		time.Now().Minute(),
		time.Now().Second(),
	)

	return config.CONFIGURATIONS.S3Config.Bucket + "/" + source +"/" + formatted + ".xml"
}