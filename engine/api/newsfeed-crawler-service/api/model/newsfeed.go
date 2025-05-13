package model

import "encoding/xml"

type NewsFeed struct {
	XMLName xml.Name `xml:"newsfeed"`
	Source Source `xml:"source"`
	Articles Articles `xml:"articles"`

}

type Source struct{
	Title string `xml:"title"`
	Link string `xml:"link"`
	Language string `xml:"language"`
	Copyright string `xml:"copyright"`
	PublicationDate string `xml:"publicationDate"`
}

type Articles struct {
	Count    int       `xml:"count,attr"`
	Articles []Article `xml:"article"`
}

type Article struct {
	UniqueIdentifier string `xml:"uniqueIdentifier,attr"`
	Headline string `xml:"headline"`
	ArticleLink string `xml:"articleLink"`
	Summary string `xml:"summary"`
	Authors Authors `xml:"authors"`
	PublishDate string `xml:"publishDate"`
	Categories Categories `xml:"categories"`
}

type Categories struct {
	Category []Category `xml:"category"`
}

type Category struct {
	Identifier string `xml:"identifier,attr"`
	Value string `xml:",chardata"`
}

type Authors struct {
	Count int `xml:"count,attr"`
	Authors []Author `xml:"authors"`
}

type Author struct {
	AuthorIdentifier string `xml:"authorIdentifier,attr"`
	Value string `xml:",chardata"`
}