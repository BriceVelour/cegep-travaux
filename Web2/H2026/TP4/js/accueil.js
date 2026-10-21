document.addEventListener("DOMContentLoaded", function() {
    renderFeaturedProducts();
});

function renderFeaturedProducts() {
    const container = document.getElementById("featured-products");

    if (container === null) {
        return;
    }

    container.innerHTML = "";

    for (let i = 0; i < 4; i++) {
        const product = products[i];

        const col = document.createElement("div");
        col.className = "col-6 col-md-3";

        const card = document.createElement("div");
        card.className = "card product-card";

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
        btn.textContent = "View Product";

        cardBody.appendChild(category);
        cardBody.appendChild(title);
        cardBody.appendChild(price);
        cardBody.appendChild(btn);

        card.appendChild(cardBody);
        col.appendChild(card);
        container.appendChild(col);
    }
}
