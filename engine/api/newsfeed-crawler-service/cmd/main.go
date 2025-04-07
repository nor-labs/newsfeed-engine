package main

import "github.com/gin-gonic/gin"
import "com.learn.newsfeed/newsfeed/api/handler"


func main(){
	router :=gin.Default()

	router.GET("/health",handler.Health)

	router.Run(":3000")
}