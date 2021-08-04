package com.difftech.hub.activities

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

interface AuthorRepository : CrudRepository<Author, String> {

	@Query("select * from authors")
	fun findAuthors(): List<Author>

}

interface MessageRepository : CrudRepository<Message, String> {

	@Query("select * from messages")
	fun findMessages(): List<Message>

	@Query("select * from messages m where m.author = :author")
	fun findMessagesOfAuthor(@Param("author") authorId: String): List<Message>
}

@Service
class AuthorService(val db: AuthorRepository) {
	fun findAuthors(): List<Author> = db.findAuthors()

	fun save(author: Author) {
		db.save(author)
	}

	fun findAuthor(id: String): Author = db.findById(id).orElseThrow { AuthorNotFoundException("Author not found!") }
}

@Service
class MessageService(val db: MessageRepository) {

	fun findMessages(): List<Message> = db.findMessages()

	fun findMessagesOfAuthor(author: String?): List<Message> = if (author != null) db.findMessagesOfAuthor(author) else findMessages()

	fun post(message: Message) {
		db.save(message)
	}
}

@SpringBootApplication
class ActivitiesHubApplication

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String, val author: String)

@Table("AUTHORS")
data class Author(@Id val id: String?, val name: String)

@ResponseStatus(HttpStatus.NOT_FOUND)
class AuthorNotFoundException(msg: String) : RuntimeException(msg)

@RestController
class MessageResource(val msgService: MessageService, val authorService: AuthorService) {
	@GetMapping("messages")
	fun getAuthorMessages(@RequestParam author: String?): List<Message> = msgService.findMessagesOfAuthor(author)

  @PostMapping(path= ["messages"],
		  consumes = [MediaType.APPLICATION_JSON_VALUE],
		  produces = [MediaType.APPLICATION_JSON_VALUE]
  )
  fun saveMessage(@RequestBody message: Message) {
	  msgService.post(message)
  }

	@GetMapping("authors")
	fun getAuthors(): List<Author> = authorService.findAuthors()

	@GetMapping("authors/{id}")
	fun getAuthor(@PathVariable id: String): Author = authorService.findAuthor(id)

	@PostMapping(path = ["authors"],
			consumes = [MediaType.APPLICATION_JSON_VALUE],
			produces = [MediaType.APPLICATION_JSON_VALUE]
	)
	fun saveAuthor(@RequestBody author: Author) {
		authorService.save(author)
	}
}

fun main(args: Array<String>) {
	runApplication<ActivitiesHubApplication>(*args)
}
