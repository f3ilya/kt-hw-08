package ru.netology

fun main() {
    println(NoteService.add("title1", "text1"))
    NoteService.add("title2", "text2")
    NoteService.add("title3", "text3")
    println(NoteService.get()[2].id)
    println()

    NoteService.edit(3, "editTitle", "editText")
    println(NoteService.get()[2].id)
    println(NoteService.get()[2].text)
    println()

    println(NoteService.get())
    println(NoteService.getById(2).text)
    NoteService.createComment(2, "fewe")
    NoteService.delete(2)
    NoteService.getComment(2)
    println(NoteService.get())
    NoteService.delete(2)
    println(NoteService.get())
    println()

    NoteService.createComment(1, "comment1")
    NoteService.createComment(1, "comment2")
    println(NoteService.getComment(1))
    println(NoteService.get())
    println()

    NoteService.deleteComment(1)
    NoteService.createComment(3, "cccc")
    println(NoteService.getComment(1))
    println(NoteService.get())
    println()

    NoteService.restoreComment(1)
    NoteService.restoreComment(1)
    NoteService.restoreComment(1)
    NoteService.restoreComment(1)
    println(NoteService.getComment(1))
    println(NoteService.get())
    println()

    NoteService.editComment(1, "12345")
    println(NoteService.getComment(1))
    println(NoteService.get())
    println()
}