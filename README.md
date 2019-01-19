## FORUM-SOLUTION

##### A collection of APIs (back-end) for a simple question answer application, i.e., a discussion forum developed using Dropwizard framework

It currently makes use of an embedded database, but can be easily configured to work with other databases by changing the corresponding properties in config.yml and adding the appropriate dependency in pom.xml

These APIs are being used by - [forum-solution-ui](https://github.com/theSaurabhMhatre/forum-solution-ui)

## APIs

##### It currently has the following APIs defined for various operations:

    POST    /forum/answers (com.forum.mod.answer.resource.AnswerResource)
    DELETE  /forum/answers/{ansId} (com.forum.mod.answer.resource.AnswerResource)
    PUT     /forum/answers/{ansId} (com.forum.mod.answer.resource.AnswerResource)
    POST    /forum/answers/{ansId}/like (com.forum.mod.answer.resource.AnswerResource)
    GET     /forum/answers/{quesId} (com.forum.mod.answer.resource.AnswerResource)
    GET     /forum/answers/{userId}/likes (com.forum.mod.answer.resource.AnswerResource)
    GET     /forum/answers/{userId}/totallikes (com.forum.mod.answer.resource.AnswerResource)
    GET     /forum/questions (com.forum.mod.question.resource.QuestionResource)
    POST    /forum/questions (com.forum.mod.question.resource.QuestionResource)
    DELETE  /forum/questions/{questionId} (com.forum.mod.question.resource.QuestionResource)
    PUT     /forum/questions/{questionId} (com.forum.mod.question.resource.QuestionResource)
    POST    /forum/questions/{questionId}/like (com.forum.mod.question.resource.QuestionResource)
    GET     /forum/questions/{userId}/likes (com.forum.mod.question.resource.QuestionResource)
    GET     /forum/questions/{userId}/totallikes (com.forum.mod.question.resource.QuestionResource)
    GET     /forum/questions/{userId}/user/answered (com.forum.mod.question.resource.QuestionResource)
    GET     /forum/questions/{userId}/user/asked (com.forum.mod.question.resource.QuestionResource)
    POST    /forum/users (com.forum.mod.user.resource.UserResource)
    GET     /forum/users/ranking (com.forum.mod.user.resource.UserResource)
    DELETE  /forum/users/{userId} (com.forum.mod.user.resource.UserResource)
    GET     /forum/users/{userId} (com.forum.mod.user.resource.UserResource)
    PUT     /forum/users/{userId} (com.forum.mod.user.resource.UserResource)
    POST    /forum/users/{userName}/validate (com.forum.mod.user.resource.UserResource)

## Dependencies

##### The following dependencies have been used:
- dropwizard-core
- dropwizard-hibernate
- dropwizard-db
- h2
