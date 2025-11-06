package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun add() {
        val result = NoteService.add("title", "text")
        assertEquals(result, 1)
    }

    @Test
    fun createComment() {
        NoteService.add("title", "text")
        val result = NoteService.createComment(1, "comment")
        assertEquals(result, 1)
    }

    @Test
    fun createCommentWithoutNote() {
        val result = NoteService.createComment(1, "fwefew")
        assertEquals(result, 0)
    }

    @Test
    fun deleteWithoutComments() {
        NoteService.add("title", "text")
        val result = NoteService.delete(1)
        assertTrue(result)
    }

    @Test
    fun deleteWithComments() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.delete(1)
        assertTrue(result)
    }

    @Test
    fun deleteWithoutNote() {
        val result = NoteService.delete(1)
        assertFalse(result)
    }

    @Test
    fun deleteComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.deleteComment(1)
        assertTrue(result)
    }

    @Test
    fun deleteCommentWithoutComment() {
        val result = NoteService.deleteComment(1)
        assertFalse(result)
    }

    @Test
    fun CommentCount() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.getById(1).comments
        assertEquals(result, 1)
    }

    @Test
    fun DeleteCommentCount() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        NoteService.deleteComment(1)
        val result = NoteService.getById(1).comments
        assertEquals(result, 0)
    }

    @Test
    fun editReturnTrue() {
        NoteService.add("title", "text")
        val result = NoteService.edit(1, "editTitle", "editText")
        assertTrue(result)
    }

    @Test
    fun editReturnFalse() {
        NoteService.add("title", "text")
        val result = NoteService.edit(2, "editTitle", "editText")
        assertFalse(result)
    }

    @Test
    fun edit() {
        NoteService.add("title", "text")
        NoteService.edit(1, "editTitle", "editText")
        val result = NoteService.getById(1).text
        assertEquals(result, "editText")
    }

    @Test
    fun editCommentTrue() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.editComment(1, "12345")
        assertTrue(result)
    }

    @Test
    fun editCommentFalseDeletedComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        NoteService.deleteComment(1)
        val result = NoteService.editComment(1, "12345")
        assertFalse(result)
    }

    @Test
    fun editCommentFalseWithoutComment() {
        NoteService.add("title", "text")
        val result = NoteService.editComment(1, "12345")
        assertFalse(result)
    }

    @Test
    fun editComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        NoteService.editComment(1, "12345")
        val result = NoteService.getComment(1)[0].message
        assertEquals(result, "12345")
    }

    @Test
    fun get() {
        NoteService.add("title", "text")
        val result = NoteService.get()[0].text
        assertEquals(result, "text")
    }

    @Test(expected = NoteNotFoundException::class)
    fun getException() {
        val result = NoteService.get()
    }

    @Test
    fun getById() {
        NoteService.add("title", "text")
        val result = NoteService.getById(1).text
        assertEquals(result, "text")
    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdException() {
        NoteService.getById(1)
    }

    @Test
    fun getComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.getComment(1)[0].message
        assertEquals(result, "comment")
    }

    @Test
    fun getCommentWithoutComment() {
        NoteService.add("title", "text")
        val result = NoteService.getComment(1)
        assertEquals(result, mutableListOf<Comment>())
    }

    @Test
    fun restoreComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        NoteService.deleteComment(1)
        val result = NoteService.restoreComment(1)
        assertTrue(result)
    }

    @Test
    fun restoreCommentWithoutComment() {
        NoteService.add("title", "text")
        val result = NoteService.restoreComment(1)
        assertFalse(result)
    }

    @Test
    fun restoreCommentWithComment() {
        NoteService.add("title", "text")
        NoteService.createComment(1, "comment")
        val result = NoteService.restoreComment(1)
        assertFalse(result)
    }
}