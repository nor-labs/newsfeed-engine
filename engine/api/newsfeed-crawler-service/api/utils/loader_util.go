package utils

import (
	"fmt"
	"io"
	"net/http"
	"strings"

	"com.learn.newsfeed/newsfeed/api/model"
	"github.com/google/uuid"
)



func GetContent(url string) (string, error) {
	resp, err := http.Get(url)

	if err != nil {
		return "", err
	}

	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return "", fmt.Errorf("failed to fetch content: %s", resp.Status)
	}

	content, err := io.ReadAll(resp.Body)
	
	if err != nil {
		return "", fmt.Errorf("Error reading response body:", err)
	}

	return string(content), nil
}

func MapNewsFeed(sourceContent model.RSS)(model.NewsFeed){

	var articlesList []model.Article
	
	newsFeed := model.NewsFeed{
		Source: model.Source{
			Title: 				sourceContent.Channel.Title,
			Copyright: 			sourceContent.Channel.Copyright,
			Language: 			sourceContent.Channel.Language,
			PublicationDate: 	sourceContent.Channel.PubDate,
		},
	}
	for _, item := range sourceContent.Channel.Items  {

		authorList := ExtractAuthors(item.Creator)

		article := model.Article{
			UniqueIdentifier: 	"urn:nytimes:article:"+uuid.New().String(),
			Headline: 			item.Title,
			ArticleLink: 		item.GUID,
			Summary: 			item.Description,
			PublishDate: 		item.PubDate,
			Authors: model.Authors{
				Count: len(authorList),
				Authors: authorList,
			},
		}

		if len(item.Categories) > 0  {
			var categoryList []model.Category			
			for _, category := range item.Categories {
				categoryList = append(categoryList, model.Category{
					Identifier: "urn:nytimes:cat:" + category,
					Value: 		category,
				})
			}

			article.Categories = model.Categories{
				Category: categoryList,
			}
		}

		articlesList = append(articlesList, article) 
	}

	newsFeed.Articles = model.Articles{
		Count: len(articlesList),
		Articles: articlesList,
	}

	return newsFeed
}


func ExtractAuthors(source string)([]model.Author){

	if strings.Contains(source,"and "){
		source = strings.ReplaceAll(source,"and ",",")
	}
	
	authorList := strings.Split(source, ",")

	var authors []model.Author

	for _, author := range authorList {
		author = strings.TrimSpace(author)
		if author != "" {
			authors = append(authors, model.Author{
				AuthorIdentifier: "urn:nytimes:author:"+uuid.New().String() ,
				Value: author,
			})
		}
	}

	return authors
}