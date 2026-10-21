let currentProduct = null;
let selectedSize = "";
let selectedColor = "";
let selectedQuantity = 1;

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = parseInt(urlParams.get("id"));

    if (!productId) {
        window.location.href = "produits.html";
        return;
    }

    currentProduct = findProductById(productId);

    if (currentProduct === null) {
        window.location.href = "produits.html";
        return;
    }

    renderProduct();
    renderRelatedProducts();
});

function renderProduct() {
    const mainImage = document.getElementById("main-image");
    mainImage.src = currentProduct.image;
    mainImage.alt = currentProduct.name;

    const productName = document.getElementById("product-name");
    productName.textContent = currentProduct.name;

    const productCategory = document.getElementById("product-category");
    productCategory.textContent = currentProduct.category;

    const productPrice = document.getElementById("product-price");
    productPrice.textContent = "$" + currentProduct.price.toFixed(2);

    const productDescription = document.getElementById("product-description");
    productDescription.textContent = currentProduct.description;

    document.title = currentProduct.name + " - ModaStyle";

    renderSizes();
    renderColors();

    const addBtn = document.getElementById("btn-add-cart");
    addBtn.addEventListener("click", function() {
        addProductToCart();
    });

    const btnMinus = document.getElementById("btn-minus");
    btnMinus.addEventListener("click", function() {
        if (selectedQuantity > 1) {
            selectedQuantity = selectedQuantity - 1;
            document.getElementById("quantity-value").textContent = selectedQuantity;
        }
    });

    const btnPlus = document.getElementById("btn-plus");
    btnPlus.addEventListener("click", function() {
        if (selectedQuantity < 10) {
            selectedQuantity = selectedQuantity + 1;
            document.getElementById("quantity-value").textContent = selectedQuantity;
        }
    });
}

function renderSizes() {
    const container = document.getElementById("size-options");
    container.innerHTML = "";

    for (const size of currentProduct.sizes) {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "option-btn";
        btn.textContent = size;
        btn.dataset.size = size;

        btn.addEventListener("click", function() {
            const allButtons = container.querySelectorAll(".option-btn");
            for (const b of allButtons) {
                b.classList.remove("selected");
            }
            this.classList.add("selected");
            selectedSize = this.dataset.size;
        });

        container.appendChild(btn);
    }

    if (currentProduct.sizes.length > 0) {
        const firstBtn = container.querySelector(".option-btn");
        firstBtn.click();
    }
}

function renderColors() {
    const container = document.getElementById("color-options");
    container.innerHTML = "";

    for (const color of currentProduct.colors) {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "option-btn";
        btn.textContent = color;
        btn.dataset.color = color;

        btn.addEventListener("click", function() {
            const allButtons = container.querySelectorAll(".option-btn");
            for (const b of allButtons) {
                b.classList.remove("selected");
            }
            this.classList.add("selected");
            selectedColor = this.dataset.color;
        });

        container.appendChild(btn);
    }

    if (currentProduct.colors.length > 0) {
        const firstBtn = container.querySelector(".option-btn");
        firstBtn.click();
    }
}

function addProductToCart() {
    if (selectedSize === "" || selectedColor === "") {
        alert("Please select a size and a color");
        return;
    }

    const success = addToCart(currentProduct.id, selectedSize, selectedColor, selectedQuantity);

    if (success) {
        showToast("Product added to cart!");
    }
}

function showToast(message) {
    let container = document.querySelector(".toast-container");

    if (container === null) {
        container = document.createElement("div");
        container.className = "toast-container";
        document.body.appendChild(container);
    }

    const toast = document.createElement("div");
    toast.className = "toast-custom success";
    toast.innerHTML = '<i class="bi bi-check-circle me-2"></i>' + message;

    container.appendChild(toast);

    setTimeout(function() {
        toast.remove();
    }, 3000);
}

function renderRelatedProducts() {
    const container = document.getElementById("related-products");
    const relatedSection = document.getElementById("related-section");

    if (container === null) {
        return;
    }

    let relatedProducts = [];
    for (const product of products) {
        if (product.category === currentProduct.category && product.id !== currentProduct.id) {
            relatedProducts.push(product);
        }
    }

    if (relatedProducts.length === 0) {
        relatedSection.classList.add("d-none");
        return;
    }

    container.innerHTML = "";

    let count = 0;
    for (const product of relatedProducts) {
        if (count >= 4) {
            break;
        }

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

        count = count + 1;
    }
}
