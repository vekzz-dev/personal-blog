@import io.github.vekzzdev.personalblog.Article

@param Article article

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${article.title()} - Personal Blog</title>
    <link rel="stylesheet" type="text/css" href="/normalize.css">
    <link rel="stylesheet" type="text/css" href="/styles.css">
</head>

<body>
    <header>
        <p class="metadata">/article/${article.getId()}</p>
    </header>

    <main>
        <section class="article-detail">
            <a class="link action" href="/admin">&#8592; All articles</a>

            <div class="article-header">
                <h1 class="display">${article.title()}</h1>
                <p class="metadata">${article.getPrettyPublishedAt()}</p>
            </div>
        </section>

        <hr class="line line-strong">

        <p class="body">${article.content()}</p>
    </main>
    </body>

</html>