# Start from the official Golang image
FROM golang:1.24.2-alpine3.21

# Set environment variables
ENV GO111MODULE=on \
    CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

# Create app directory
WORKDIR /app

# Copy go.mod and go.sum
COPY go.mod go.sum ./

# Download dependencies
RUN go mod download

# Copy the entire project
COPY . .

# Build the Go app
RUN go build -o main ./cmd

# Expose the application port
EXPOSE 3000

# Command to run the executable
CMD ["./main"]
