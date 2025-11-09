package ru.netology

class NoteNotFoundException(message: String) : Exception(message)
class CommentNotFoundException(message: String) : Exception(message)

data class Comment(
    val id: Int,
    val uid: Int = 0,
    val nid: Int,
    val oid: Int = 0,
    val date: Int = 0,
    val message: String,
    val isDeleted: Boolean = false
)

data class Note(
    val id: Int,
    val title: String,
    val text: String,
    val date: Int = 0,
    val comments: Int = 0,
    val readComments: Int = 0,
    val viewUrl: String = "viewUrl"
)

object NoteService {
    private var notes: MutableList<Note> = mutableListOf()
    private var comments: MutableList<Comment> = mutableListOf()
    private var id: Int = 0
    private var commentId: Int = 0

    fun add(
        title: String,
        text: String,
        privacy: Int = 0,
        commentPrivacy: Int = 0,
        privacyView: String = "",
        privacyComment: String = ""
    ): Int {
        notes += Note(++id, title, text)
        return notes.last().id
    }

    fun createComment(noteId: Int, message: String): Int {
        for ((index, note) in notes.withIndex()) {
            if (note.id == noteId) {
                comments += Comment(++commentId, nid = noteId, message = message)
                notes[index] = note.copy(comments = note.comments + 1)
                return comments.last().id
            }
        }
        return 0
    }

    fun delete(noteId: Int): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == noteId) {
                notes.removeAt(index)
                val iter = comments.iterator()
                while (iter.hasNext()) {
                    val comment = iter.next()
                    if (comment.nid == noteId) {
                        iter.remove()
                    }
                }
                return true
            }
        }
        return false
    }

    fun deleteComment(commentId: Int): Boolean {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentId) {
                comments[index] = comment.copy(isDeleted = true)
                for ((indexNote, note) in notes.withIndex()) {
                    if (note.id == comment.nid) {
                        notes[indexNote] = note.copy(comments = note.comments - 1)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Int = 0,
        commentPrivacy: Int = 0,
        privacyView: String = "",
        privacyComment: String = ""
    ): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (note.id == noteId) {
                notes[index] = note.copy(title = title, text =  text, comments = note.comments)
                return true
            }
        }
        return false
    }

    fun editComment(commentId: Int, message: String): Boolean {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentId && !comment.isDeleted) {
                comments[index] = comment.copy(message = message)
                return true
            }
        }
        return false
    }

    fun get(
        noteIds: Int = 0,
        userId: Int = 0,
        offset: Int = 0,
        count: Int = 0,
        sort: Boolean = true
    ): MutableList<Note> {
        for (note in notes) {
            return notes
        }
        throw NoteNotFoundException("Note not found")
    }

    fun getById(noteId: Int, ownerId: Int = 0, needWiki: Boolean = false): Note {
        for (note in notes) {
            if (note.id == noteId) {
                return note
            }
        }
        throw NoteNotFoundException("Note not found")
    }

    fun getComment(
        noteId: Int,
        sort: Boolean = true,
        offset: Int = 0,
        count: Int = 0
    ): MutableList<Comment> {
        var ret: MutableList<Comment> = mutableListOf()
        for (comment in comments) {
            if (!comment.isDeleted && comment.nid == noteId)
                ret += comment
        }
        if (ret.size > 0) {
            return ret
        }
        throw CommentNotFoundException("Comment not found")
    }

    fun restoreComment(commentId: Int): Boolean {
        for ((index, comment) in comments.withIndex()) {
            if (comment.id == commentId && comment.isDeleted) {
                comments[index] = comment.copy(isDeleted = false)
                for ((indexNote, note) in notes.withIndex()) {
                    if (note.id == comment.nid) {
                        notes[indexNote] = note.copy(comments = note.comments + 1)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun clear() {
        notes.clear()
        comments.clear()
        id = 0
        commentId = 0
    }
}