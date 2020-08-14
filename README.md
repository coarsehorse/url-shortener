# Url Shortener
Main target of this project was to learn `Docker` and `Docker Compose`.\
But to dockerize some app, you first need to create this app, huh.\
And I think an url shortener is one of the best apps for this purpose.\
It is simple application that able to shorten specified link.\
Example: `https://very-long.domen.com/article-title` -> `https://sh.com/Hd51d`\
MongoDB is used as a data base to make app fast.\
iOS client: https://github.com/theimaginationless/url-shortener-ios-client

### How to build and run App + MongoDB using Docker
* `cd ./url-shortener`
* `docker-compose up --build`

Now you can access url-shortener at `localhost:8080` and mongodb at `localhost:27017`.\
MongoDB data is persisted between containers restarts.\
Db name and credentials may be found in `application.properties`.

### How to use
Once application is started you can post long url to receive shortened version:
* `POST /shortenUrl` with body 
`{ "sourceUrl": "https://very-long.domen.com/article-title" }`
* Receive something like 
`{ "sourceUrl": "https://very-long.domen.com/article-title",
 "shortenedUrl": "http://localhost/Hd51d" }`
 
And than
 * `GET /Hd51d` to be redirected to `https://very-long.domen.com/article-title`
