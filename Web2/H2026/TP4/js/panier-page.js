document.addEventListener("DOMContentLoaded", function() {
    renderCart();
});

function renderCart() {
    const cart = getCart();
    const emptyCart = document.getElementById("empty-cart");
    const cartContent = document.getElementById("cart-content");

    if (cart.length === 0) {
        emptyCart.classList.remove("d-none");
        cartContent.classList.add("d-none");
        return;
    }

    emptyCart.classList.add("d-none");
    cartContent.classList.remove("d-none");

    renderItems();
    calculateSummary();
}

function renderItems() {
    const container = document.getElementById("cart-list");
    const cart = getCart();

    container.innerHTML = "";

    for (let i = 0; i < cart.length; i++) {
        const item = cart[i];

        const div = document.createElement("div");
        div.className = "cart-item";

        const img = document.createElement("img");
        img.src = item.image;
        img.alt = item.name;

        const info = document.createElement("div");
        info.className = "info";

        const name = document.createElement("h4");
        name.className = "name";
        name.textContent = item.name;

        const details = document.createElement("p");
        details.className = "details";
        details.textContent = "Size: " + item.size + " | Color: " + item.color;

        const price = document.createElement("p");
        price.className = "price";
        price.textContent = "$" + item.price.toFixed(2);

        info.appendChild(name);
        info.appendChild(details);
        info.appendChild(price);

        const actions = document.createElement("div");
        actions.className = "actions";

        const quantityControls = document.createElement("div");
        quantityControls.className = "quantity-controls";

        const minusBtn = document.createElement("button");
        minusBtn.type = "button";
        minusBtn.innerHTML = '<i class="bi bi-dash"></i>';
        minusBtn.dataset.index = i;
        minusBtn.addEventListener("click", function() {
            changeQuantity(parseInt(this.dataset.index), -1);
        });

        const quantitySpan = document.createElement("span");
        quantitySpan.textContent = item.quantity;

        const plusBtn = document.createElement("button");
        plusBtn.type = "button";
        plusBtn.innerHTML = '<i class="bi bi-plus"></i>';
        plusBtn.dataset.index = i;
        plusBtn.addEventListener("click", function() {
            changeQuantity(parseInt(this.dataset.index), 1);
        });

        quantityControls.appendChild(minusBtn);
        quantityControls.appendChild(quantitySpan);
        quantityControls.appendChild(plusBtn);

        const removeBtn = document.createElement("button");
        removeBtn.className = "btn btn-outline-danger btn-sm mt-2";
        removeBtn.innerHTML = '<i class="bi bi-trash"></i> Remove';
        removeBtn.dataset.index = i;
        removeBtn.addEventListener("click", function() {
            removeItem(parseInt(this.dataset.index));
        });

        actions.appendChild(quantityControls);
        actions.appendChild(removeBtn);

        div.appendChild(img);
        div.appendChild(info);
        div.appendChild(actions);

        container.appendChild(div);
    }
}

function changeQuantity(index, delta) {
    const cart = getCart();
    const newQuantity = cart[index].quantity + delta;

    if (newQuantity <= 0) {
        removeItem(index);
        return;
    }

    if (newQuantity > 10) {
        return;
    }

    updateQuantity(index, newQuantity);
    renderCart();
}

function removeItem(index) {
    removeFromCart(index);
    renderCart();
}

function confirmClearCart() {
    const confirmation = confirm("Are you sure you want to clear your cart?");
    if (confirmation) {
        clearCart();
        renderCart();
    }
}

function calculateSummary() {
    const subtotal = calculateTotal();

    let shipping = 9.99;
    if (subtotal >= 50) {
        shipping = 0;
    }

    const taxes = subtotal * 0.15;
    const total = subtotal + shipping + taxes;

    document.getElementById("subtotal").textContent = "$" + subtotal.toFixed(2);

    if (shipping === 0) {
        document.getElementById("shipping").textContent = "Free";
    } else {
        document.getElementById("shipping").textContent = "$" + shipping.toFixed(2);
    }

    document.getElementById("taxes").textContent = "$" + taxes.toFixed(2);
    document.getElementById("total").textContent = "$" + total.toFixed(2);
}
