let activeCategory = "all";
let searchQuery = "";
let sortBy = "default";

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryParam = urlParams.get("category");

    if (categoryParam) {
        activeCategory = categoryParam;
    }

    renderCategories();
    renderProducts();

    const searchInput = document.getElementById("search");
    searchInput.addEventListener("input", function() {
        searchQuery = this.value.toLowerCase();
        renderProducts();
    });

    const sortSelect = document.getElementById("sort");
    sortSelect.addEventListener("change", function() {
        sortBy = this.value;
        renderProducts();
    });
});

function renderCategories() {
    const container = document.getElementById("category-filters");
    container.innerHTML = "";

    for (const category of categories) {
        const btn = document.createElement("button");
        btn.className = "btn btn-outline-secondary";
        btn.textContent = category.name;
        btn.dataset.category = category.id;

        if (category.id === activeCategory) {
            btn.classList.add("active");
        }

        btn.addEventListener("click", function() {
            activeCategory = this.dataset.category;

            const allButtons = container.querySelectorAll(".btn");
            for (const b of allButtons) {
                b.classList.remove("active");
            }
            this.classList.add("active");

            renderProducts();
        });

        container.appendChild(btn);
    }
}

function filterProducts() {
    let results = [];

    for (const product of products) {
        let matchCategory = false;
        let matchSearch = false;

        if (activeCategory === "all") {
            matchCategory = true;
        } else if (product.category === activeCategory) {
            matchCategory = true;
        }

        if (searchQuery === "") {
            matchSearch = true;
        } else {
            const nameLower = product.name.toLowerCase();
            const descLower = product.description.toLowerCase();
            const catLower = product.category.toLowerCase();

            if (nameLower.includes(searchQuery)) {
                matchSearch = true;
            }
            if (descLower.includes(searchQuery)) {
                matchSearch = true;
            }
            if (catLower.includes(searchQuery)) {
                matchSearch = true;
            }
        }

        if (matchCategory && matchSearch) {
            results.push(product);
        }
    }

    if (sortBy === "price-asc") {
        results.sort(function(a, b) {
            return a.price - b.price;
        });
    } else if (sortBy === "price-desc") {
        results.sort(function(a, b) {
            return b.price - a.price;
        });
    } else if (sortBy === "name") {
        results.sort(function(a, b) {
            if (a.name < b.name) {
                return -1;
            }
            if (a.name > b.name) {
                return 1;
            }
            return 0;
        });
    }

    return results;
}

function renderProducts() {
    const container = document.getElementById("product-list");
    const noResultsMessage = document.getElementById("no-results");
    const resultsCount = document.getElementById("results-count");

    const filteredProducts = filterProducts();

    if (filteredProducts.length === 1) {
        resultsCount.textContent = "1 product";
    } else {
        resultsCount.textContent = filteredProducts.length + " products";
    }

    container.innerHTML = "";

    if (filteredProducts.length === 0) {
        noResultsMessage.classList.remove("d-none");
        return;
    }

    noResultsMessage.classList.add("d-none");

    for (const product of filteredProducts) {
        const col = document.createElement("div");
        col.className = "col-6 col-md-4 col-lg-3";

        const card = document.createElement("div");
        card.className = "card product-card fade-in";

        const link = document.createElement("a");
        link.href = "produit.html?id=" + product.id;

        const img = document.createElement("img");
        img.src = product.image;
        img.alt = product.name;
        img.className = "card-img-top";

        link.appendChild(img);
        card.appendChild(link);

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const category = document.createElement("span");
        category.className = "category";
        category.textContent = product.category;

        const title = document.createElement("h5");
        title.className = "card-title";
        title.textContent = product.name;

        const price = document.createElement("p");
        price.className = "price";
        price.textContent = "$" + product.price.toFixed(2);

        const btn = document.createElement("a");
        btn.href = "produit.html?id=" + product.id;
        btn.className = "btn btn-primary w-100";
        btn.innerHTML = '<i class="bi bi-eye me-2"></i>View Product';

        cardBody.appendChild(category);
        cardBody.appendChild(title);
        cardBody.appendChild(price);
        cardBody.appendChild(btn);

        card.appendChild(cardBody);
        col.appendChild(card);
        container.appendChild(col);
    }
}

function resetFilters() {
    activeCategory = "all";
    searchQuery = "";
    sortBy = "default";

    document.getElementById("search").value = "";
    document.getElementById("sort").value = "default";

    renderCategories();
    renderProducts();
}
