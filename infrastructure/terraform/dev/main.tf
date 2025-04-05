terraform {
    required_providers {
        aws = {
            source = "hashicorp/aws"
            version = "~> 4.16"
        }
    }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_ecr_repository" "newsfeed-crawler-service-ecr-repo"{

    name  = "newsfeed-crawler-service"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

      lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }
}

resource "aws_ecr_repository" "newsfeed-parser-service-ecr-repo"{

    name  = "newsfeed-parser-service"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

      lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }

}

resource "aws_ecr_repository" "newsfeed-ingestor-service-ecr-repo"{

    name  = "newsfeed-ingestor-service"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

      lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }
}

resource "aws_ecr_repository" "newsfeed-searcher-service-ecr-repo"{

    name  = "newsfeed-searcher-service"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

      lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }
}

resource "aws_ecr_repository" "newsfeed-engine-solr-ecr-repo"{

    name  = "newsfeed-engine-solr"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

      lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }
}

resource "aws_ecr_repository" "newsfeed-engine-authentication-service-ecr-repo"{

    name  = "newsfeed-engine-authentication-service"

    image_tag_mutability  = "MUTABLE"

    force_delete = false

    image_scanning_configuration{
    scan_on_push = false
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

    lifecycle {
    prevent_destroy = true  # Prevents accidental deletion
  }
}

# buckets creation

resource "aws_s3_bucket" "news-feed"{
    bucket = "2025-news-feed"
    force_destroy = false

}