package config

import (
	"os"
	"path/filepath"

	"gopkg.in/yaml.v3"
)
type Wrapper struct {
    Config Config `yaml:"config"`
}

type Config struct {
	Loader struct {
		ArticleIdentifierPrefix  string `yaml:"articleIdentifierPrefix"`
		CategoryIdentifierPrefix string `yaml:"categoryIdentifierPrefix"`  
		AuthorIdentifierPrefix string `yaml:"authorIdentifierPrefix"` 
		ResourceUriPrefix string `yaml:"resourceUriPrefix"`   
	} `yaml:"loader"`

	Sources []string `yaml:"sources"`
	
	S3Config struct {
		Bucket string `yaml:"bucket"`
	} `yaml:"s3"`

	SQS struct {
		QueueUrl string `yaml:"queueUrl"`
	} `yaml:"sqs"`
}

var CONFIGURATIONS Config

func LoadConfig(){
	path := filepath.Join("api", "config", "config.yaml")
	yamlBytes , err := os.ReadFile(path)
	if err != nil {
		panic(err)
	}
	yamlString := os.ExpandEnv(string(yamlBytes))
	var wrapper Wrapper
	err = yaml.Unmarshal([]byte(yamlString), &wrapper)
	if err != nil {
		panic(err)
	}
	CONFIGURATIONS = wrapper.Config
}