package model

import "encoding/xml"

type RSS struct {
    XMLName xml.Name `xml:"rss"`
    Channel Channel  `xml:"channel"`
}

type Channel struct {
    Title       string  `xml:"title"`
    // Link        string  `xml:"link"`
    Description string  `xml:"description"`
    Copyright string `xml:"copyright"`
    Language    string  `xml:"language"`
    LastBuild   string  `xml:"lastBuildDate"`
    PubDate     string  `xml:"pubDate"`
    Image       Image   `xml:"image"`
    Items       []Item  `xml:"item"`
}

type Image struct {
    Title string `xml:"title"`
    URL   string `xml:"url"`
    Link  string `xml:"link"`
}

type Item struct {
    Title       string   `xml:"title"`
    Link        string   `xml:"link"`
    GUID        string   `xml:"guid"`
    Description string   `xml:"description"`
    Creator     string   `xml:"creator"`
    PubDate     string   `xml:"pubDate"`
    Categories  []string `xml:"category"`
    Media       Media    `xml:"media:content"`
}

type Media struct {
    URL    string `xml:"url,attr"`
    Height int    `xml:"height,attr"`
    Width  int    `xml:"width,attr"`
}
