<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New article - Personal Blog</title>
    <link rel="stylesheet" type="text/css" href="/normalize.css">
    <link rel="stylesheet" type="text/css" href="/styles.css">
</head>

<body>
    <header>
        <p class="metadata">/new</p>
    </header>

    <main>
        <section class="new">
            <h1 class="section-title">New article</h1>

            <form class="article-form" action="/admin/create" method="POST">
                <label class="label-text" for="title">Article title</label>
                <input class="input field-value" type="text" name="title" id="title"
                    placeholder="Give the article title" required>

                <label class="label-text" for="publish_date">Publishing date</label>
                <input class="input field-value" type="datetime-local" name="publish_date" id="publish_date" required>

                <label class="label-text" for="content">Content</label>
                <textarea class="input field-value input-textarea" name="content" id="content" rows="10"
                    placeholder="Write the article here. A few honest paragraphs are enough to start."
                    required></textarea>

                <div class="form-action">
                    <button class="btn btn--primary" type="submit">Publish</button>
                    <a class="btn btn--secondary" href="/admin">Cancel</a>
                </div>
            </form>
        </section>
    </main>
</body>

</html>