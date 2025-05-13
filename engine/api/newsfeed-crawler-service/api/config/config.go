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
}

var CONFIGURATIONS Config

func LoadConfig(){
	path := filepath.Join("api", "config", "config.yaml")
	yamlFile , err := os.ReadFile(path)
	if err != nil {
		panic(err)
	}

	var wrapper Wrapper
	err = yaml.Unmarshal(yamlFile, &wrapper)
	if err != nil {
		panic(err)
	}
	CONFIGURATIONS = wrapper.Config
}