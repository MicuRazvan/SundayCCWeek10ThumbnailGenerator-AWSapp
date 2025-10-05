# Week 10 Project — Thumbnail Generator AWS App

## Context
I lost a bet with a friend and he challenged me that for the next 52 weeks, during weekends I need to create from scratch a new project.

For this weekend we decided on a AWS app and went with a Thumbnail Generator using Amazon S3.

## The rules are the following:
*   Each Friday night, me and him will talk about what project I need to do.
*   Mostly he will decide for me, but I’m allowed to suggest and do my own ideas if he agrees on them.
*   Once the project is decided, he will tell me if I’m allowed to work Saturday and Sunday, or only Sunday.
(Surely this won’t backfire at some point by underestimating a project, right? 😅)...well, it kinda did, didnt finish it in weekend.

## Features
*   Converts all the images from /imagesToConvert directory into Thumbnails and saves them in /convertedImages directory.
*   You can set manually the sizes you want to be converted in by modifying THUMBNAIL_HEIGHT and THUMBNAIL_WIDTH in ImageProcessor.java

## How to run it

To avoid making your own AWS account I added docker to it.

### Prerequisites

-   [Docker](https://www.docker.com/get-started) must be installed and running.

### 1. Build the Docker Image

From the root of the project directory, run the following command. This will create a local Docker image named `thumbnail-generator`.

```bash
docker build -t thumbnail-generator .
```

### 2. Run the Docker Image

On Linux or macOS:
```bash
docker run --rm -v "$(pwd)/imagesToConvert":/app/imagesToConvert -v "$(pwd)/convertedImages":/app/convertedImages thumbnail-generator
```

On Windows (Command Prompt):
```bash
docker run --rm -v "%cd%/imagesToConvert":/app/imagesToConvert -v "%cd%/convertedImages":/app/convertedImages thumbnail-generator
```
