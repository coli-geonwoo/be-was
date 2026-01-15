const urlParams = new URLSearchParams(window.location.search);
const initialOffset = urlParams.get('offset');
let offset = initialOffset ? parseInt(initialOffset) : 0;
let total = 0;
let images = [];
let imageIndex = 0;
let allComments = [];
let articleId = null;

const likeBtn = document.getElementById("like-btn");
const likeCountEl = document.getElementById("like-count");

document.addEventListener("DOMContentLoaded", () => {
    loadArticle();

    document.getElementById("like-btn").onclick = handleLikeClick;

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

    document.getElementById("write-comment-btn").onclick = () => {
        if (!articleId) return;
        location.href = `/comment?articleId=${articleId}`;
    };

    document.getElementById("next-image").onclick = () => changeImage(1);
    document.getElementById("prev-image").onclick = () => changeImage(-1);

    document.getElementById("show-all-btn").onclick = renderAllComments;
});

async function loadArticle() {
    const res = await fetch(`/article/latest?offset=${offset}`);

    if (res.status === 400) {
        console.log("show empty state")
        showEmptyState();
        return;
    }
    const article = await res.json();
    total = article.total;
    articleId = article.articleId;
    console.log("hide empty state")
    hideEmptyState()

    //계정정보 치환
    document.querySelector(".post__account__nickname").textContent =
        article.nickname;

    const profileImg = document.querySelector(".post__account__img");
    profileImg.src = article.imageUrl;
    profileImg.alt = article.nickname;

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

async function handleLikeClick() {
    if (!articleId) return;
    if (likeBtn.disabled) return; //이미 POST를 보낸 상황에서는 요청 무시하기
    likeBtn.disabled = true;

    try {
        const res = await fetch(`/article/likes?articleId=` + articleId, {
            method: "POST",
        });

        if (!res.ok) return;

        const data = await res.json(); // { count: number }

        // 좋아요 수 갱신
        const countEl = document.getElementById("like-count");
        countEl.textContent = data.count;

        animateLikeButton();
    } catch (e) {
        console.error("like error", e);
    } finally {
        likeBtn.disabled = false; //다시 활성화하기
    }
}

function animateLikeButton() {
    const btn = document.getElementById("like-btn");

    btn.classList.add("liked");

    setTimeout(() => {
        btn.classList.remove("liked");
    }, 150);
}


function showEmptyState() {
    document.getElementById("wrapper").style.display = "none";
    document.getElementById("empty-state").style.display = "flex";

    // 네비게이션 버튼 숨김
    document.getElementById("prev-article").style.display = "none";
    document.getElementById("next-article").style.display = "none";
}

function hideEmptyState() {
    document.getElementById("wrapper").style.display = "block";
    document.getElementById("empty-state").style.display = "none";
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
    btn.textContent = `모든 댓글보기 (${allComments.length})`;
    if (allComments.length > 3) {
        btn.textContent = `모든 댓글 보기 (${allComments.length})`;
        btn.classList.remove("hidden");
    } else {
        btn.classList.add("hidden");
    }
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
