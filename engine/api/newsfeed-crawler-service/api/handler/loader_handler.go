package handler

import (
	"net/http"

	"com.learn.newsfeed/newsfeed/api/service"
	"github.com/gin-gonic/gin"
)


func LoadContent(ctx *gin.Context){
	resp := service.LoadContent(ctx.Request.Context())
	ctx.JSON(http.StatusOK, resp)
}