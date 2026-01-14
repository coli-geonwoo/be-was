let offset = 0;
let total = 0;
let images = [];
let imageIndex = 0;
let allComments = [];

document.addEventListener("DOMContentLoaded", () => {
    loadArticle();

    document.getElementById("next-article").onclick = () => {
        if (offset < total - 1) {
            offset++;
            loadArticle();
        }
    };

    document.getElementById("prev-article").onclick = () => {
        if (offset > 0) {
            offset--;
            loadArticle();
        }
    };

    document.getElementById("next-image").onclick = () => changeImage(1);
    document.getElementById("prev-image").onclick = () => changeImage(-1);

    document.getElementById("show-all-btn").onclick = renderAllComments;
});

async function loadArticle() {
    const res = await fetch(`/article/latest?offset=${offset}`);
    const article = await res.json();

    total = article.total;

    // 제목 / 내용
    document.getElementById("post-title").textContent = article.title;
    document.getElementById("post-content").textContent = article.content;

    // 이미지
    images = article.images || [];
    imageIndex = 0;
    renderImage();

    // 좋아요 / 댓글 수
    document.getElementById("like-count").textContent = article.likes;
    document.getElementById("comment-count").textContent = article.comments.count;

    // 댓글
    allComments = article.comments.contents || [];
    renderTopComments();
    updateNavButtons()
}

function renderImage() {
    document.getElementById("post-image").src = images[imageIndex] || "";
}

function changeImage(step) {
    if (images.length === 0) return;
    imageIndex = (imageIndex + step + images.length) % images.length;
    renderImage();
}

function renderComment(c) {
    const li = document.createElement("li");
    li.className = "comment__item";
    li.innerHTML = `
    <div class="comment__item__user">
      <img src="${c.imageUrl}" class="comment__item__user__img"/>
      <p class="comment__item__user__nickname">${c.nickname}</p>
    </div>
    <p class="comment__item__article">${c.content}</p>
  `;
    document.getElementById("comment-items").appendChild(li);
}

function renderTopComments() {
    const list = document.getElementById("comment-items");
    list.classList.remove("expanded");
    list.innerHTML = "";

    allComments.slice(0, 3).forEach(renderComment);

    const btn = document.getElementById("show-all-btn");
    btn.classList.toggle("hidden", allComments.length <= 3);
}

function renderAllComments() {
    const list = document.getElementById("comment-items");
    list.innerHTML = "";

    allComments.forEach(renderComment);
    list.classList.add("expanded");

    document.getElementById("show-all-btn").classList.add("hidden");
}

function updateNavButtons() {
    const prevBtn = document.getElementById("prev-article");
    const nextBtn = document.getElementById("next-article");

    prevBtn.style.display = offset === 0 ? "none" : "flex";
    nextBtn.style.display = offset >= total - 1 ? "none" : "flex";
}
