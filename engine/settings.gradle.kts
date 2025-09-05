rootProject.name = "engine"
include("api")
include("api:newsfeed-engine-authentication-service")
findProject(":api:newsfeed-engine-authentication-service")?.name = "newsfeed-engine-authentication-service"
include("api:newsfeed-ingestor-service")
findProject(":api:newsfeed-ingestor-service")?.name = "newsfeed-ingestor-service"
include("api:newsfeed-parser-service")
findProject(":api:newsfeed-parser-service")?.name = "newsfeed-parser-service"
include("api:newsfeed-searcher-service")
findProject(":api:newsfeed-searcher-service")?.name = "newsfeed-searcher-service"

include("libs:content-fetcher")

include("libs:event-log-lib")