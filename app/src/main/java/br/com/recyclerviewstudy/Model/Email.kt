package br.com.recyclerviewstudy.Model

data class Email(val user: String,
                 val subject : String,
                 val preview: String,
                 val date: String,
                 val stared: Boolean,
                 val unread: Boolean,
                 var selected: Boolean = false)

class EmailBuilder{
    var user: String = ""
    var subject : String = ""
    var preview: String = ""
    var date: String = ""
    var stared: Boolean = false
    var unread: Boolean = false

    fun build(): Email = Email(user, subject, preview, date, stared, unread, false)
}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build()

fun fakeEmails() = mutableListOf(
        email{
            user = "Facebook"
            subject = "Veja nossas dicas para aprender definitivamente RecyclerView"
            preview = "Olá meu jovem, acesse já!"
            date = "02 Fev"
            stared = false
        },
        email{
            user = "Facebook"
            subject = "Concluiíu a leitura sobre RecyclerView?"
            preview = "Fulano convidou você a participar do grupo fechado"
            date = "02 Fev"
            stared = false
        },
        email{
            user = "YouTube"
            subject = "Victor Leonardo acabou de enviar um vídeo"
            preview = "Victor enviou Android: RECYCLERVIEW"
            date = "02 Fev"
            stared = true
            unread = true
        },
        email{
            user = "Instagram"
            subject = "5 dicas para você aprender RecyclerView"
            preview = "Olá João, acesse o site e confira!"
            date = "10 Fev"
            stared = false
        },
        email{
            user = "Facebook"
            subject = "Veja nossas dicas para aprender definitivamente RecyclerView"
            preview = "Olá meu jovem, acesse já!"
            date = "02 Fev"
            stared = false
        },
        email{
            user = "Facebook"
            subject = "Concluiíu a leitura sobre RecyclerView?"
            preview = "Fulano convidou você a participar do grupo fechado"
            date = "02 Fev"
            stared = false
        },
        email{
            user = "YouTube"
            subject = "Victor Leonardo acabou de enviar um vídeo"
            preview = "Victor enviou Android: RECYCLERVIEW"
            date = "02 Fev"
            stared = true
            unread = true
        },
        email{
            user = "Instagram"
            subject = "5 dicas para você aprender RecyclerView"
            preview = "Olá João, acesse o site e confira!"
            date = "10 Fev"
            stared = false
        }



)