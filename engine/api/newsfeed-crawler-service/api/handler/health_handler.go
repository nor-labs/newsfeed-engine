package handler
import (
	"net/http"
	"time"
	"github.com/gin-gonic/gin"
)

func Health(ctx *gin.Context){
	ctx.JSON(http.StatusOK, gin.H{
		"timestamp":time.Now(),
		"message":"Healthy",
	})
}
