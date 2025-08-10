package main

import "github.com/gin-gonic/gin"
import "com.learn.newsfeed/newsfeed/api/handler"
import "com.learn.newsfeed/newsfeed/api/config"


func main(){
	
	config.LoadConfig()

	router :=gin.Default()

	router.GET("/api/health",handler.Health)
	router.GET("/api/load",handler.LoadContent)


	router.Run(":3000")
}