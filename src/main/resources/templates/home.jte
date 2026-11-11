@import java.util.List
@import io.github.vekzzdev.personalblog.Article

@param List<Article> articles

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Personal Blog</title>
    <link rel="stylesheet" type="text/css" href="/normalize.css">
    <link rel="stylesheet" type="text/css" href="/styles.css">
</head>

<body>
<header>
    <p class="metadata">/home</p>
</header>

<main>
    <section class="div-title">
        <h1 class="wordmark">Personal Blog<span class="dot">.</span></h1>
        <a class="link action" href="/admin">Admin &#8599;</a>
    </section>

    <hr class="line line-strong">

    <ul class="list">
        @for(var article : articles)
            <li class="row">
                <h2 class="row-title">
                    <a href="/home/article/${article.getId()}">
                        ${article.title()}
                    </a>
                </h2>
                <p class="metadata">${article.getPrettyPublishedAt()}</p>
            </li>

            @if(articles.listIterator().hasNext())
                <hr class="line">
            @endif
        @endfor
    </ul>
</main>
</body>

</html>