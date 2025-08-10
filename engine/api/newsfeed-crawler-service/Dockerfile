FROM golang:1.24.2-alpine3.21

ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

WORKDIR /app

COPY go.mod go.sum ./


RUN go mod download

COPY . .


RUN go build -o main ./api/cmd


EXPOSE 3000

CMD ["./main"]
