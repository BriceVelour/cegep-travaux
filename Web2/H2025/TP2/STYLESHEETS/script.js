document.addEventListener("DOMContentLoaded", function () {
  let gamesSection = document.getElementById("games-section");
  let gamesNavItem = document.querySelector(".nav-item");
  let searchIcon = document.querySelector(".bi-search");
  let searchBox = document.querySelector(".search-box");
  let closeSearchBtn = document.querySelector(".close-search-btn");
  let loginIcon = document.querySelector(".bi-person-circle");
  let loginModal = new bootstrap.Modal(document.getElementById("loginModal"));

  gamesNavItem.addEventListener("mouseenter", function () {
    gamesSection.classList.add("show");
  });

  gamesNavItem.addEventListener("mouseleave", function () {
    setTimeout(() => {
      if (!gamesSection.matches(":hover")) {
        gamesSection.classList.remove("show");
      }
    }, 500);
  });

  gamesSection.addEventListener("mouseleave", function () {
    setTimeout(() => {
      if (!gamesNavItem.matches(":hover")) {
        gamesSection.classList.remove("show");
      }
    }, 500);
  });

  searchIcon.addEventListener("click", function () {
    searchBox.classList.add("active");
  });

  closeSearchBtn.addEventListener("click", function () {
    searchBox.classList.remove("active");
  });

  loginIcon.addEventListener("click", function () {
    loginModal.show();
  });

  // Fermer la boîte de recherche en cliquant en dehors
  document.addEventListener("click", function (event) {
    if (!searchBox.contains(event.target) && event.target !== searchIcon) {
      searchBox.classList.remove("active");
    }
  });
});
