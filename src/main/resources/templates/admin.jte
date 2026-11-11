@import java.util.List
@import io.github.vekzzdev.personalblog.Article

@param List<Article> articles

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Personal Blog</title>
    <link rel="stylesheet" type="text/css" href="/normalize.css">
    <link rel="stylesheet" type="text/css" href="/styles.css">
</head>

<body>
    <header>
        <p class="metadata">/admin</p>
    </header>

    <main>

        <section class="div-title">
            <h1 class="wordmark">Personal Blog<span class="dot">.</span></h1>
            <div>
                <a class="link danger" href="/home">Log out &#8601;</a>
                <a class="btn btn--primary" href="/admin/new">+ Add</a>
            </div>

        </section>

        <hr class="line line-strong">

        <ul class="list">
            @for(var article : articles)
            <li class="row">
                <h2 class="row-title">
                    <a href="/admin/article/${article.getId()}">
                        ${article.title()}
                    </a>
                </h2>
                <div class="div-editor">
                    <p class="metadata">${article.getPrettyPublishedAt()}</p>
                    <p class="editor edit reaction">
                        <a href="/admin/edit/${article.getId()}">Edit</a>
                    </p>
                    <p class="editor delete danger">
                        <a href="/admin/delete/${article.getId()}">Delete</a>
                    </p>
                </div>
            </li>

                @if(articles.indexOf(article) < articles.size() - 1)
                    <hr class="line">
                @endif
            @endfor

        </ul>
    </main>
</body>

</html>