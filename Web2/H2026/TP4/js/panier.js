function getCart() {
    const cartData = localStorage.getItem("cart");
    if (cartData) {
        return JSON.parse(cartData);
    }
    return [];
}

function saveCart(cart) {
    localStorage.setItem("cart", JSON.stringify(cart));
    updateCartCount();
}

function findProductById(id) {
    for (const product of products) {
        if (product.id === id) {
            return product;
        }
    }
    return null;
}

function addToCart(productId, size, color, quantity) {
    const cart = getCart();
    const product = findProductById(productId);

    if (product === null) {
        return false;
    }

    let found = false;
    for (const item of cart) {
        if (item.id === productId && item.size === size && item.color === color) {
            item.quantity = item.quantity + quantity;
            found = true;
        }
    }

    if (found === false) {
        const newItem = {
            id: productId,
            name: product.name,
            price: product.price,
            image: product.image,
            size: size,
            color: color,
            quantity: quantity
        };
        cart.push(newItem);
    }

    saveCart(cart);
    return true;
}

function removeFromCart(index) {
    const cart = getCart();
    cart.splice(index, 1);
    saveCart(cart);
}

function updateQuantity(index, newQuantity) {
    const cart = getCart();
    if (newQuantity <= 0) {
        removeFromCart(index);
    } else {
        cart[index].quantity = newQuantity;
        saveCart(cart);
    }
}

function calculateTotal() {
    const cart = getCart();
    let total = 0;
    for (const item of cart) {
        total = total + (item.price * item.quantity);
    }
    return total;
}

function countItems() {
    const cart = getCart();
    let count = 0;
    for (const item of cart) {
        count = count + item.quantity;
    }
    return count;
}

function updateCartCount() {
    const countElements = document.querySelectorAll(".cart-count");
    const count = countItems();

    for (const element of countElements) {
        element.textContent = count;
        if (count > 0) {
            element.classList.remove("d-none");
        } else {
            element.classList.add("d-none");
        }
    }
}

function clearCart() {
    localStorage.removeItem("cart");
    updateCartCount();
}

document.addEventListener("DOMContentLoaded", function() {
    updateCartCount();
});
