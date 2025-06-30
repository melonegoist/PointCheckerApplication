import { ref } from "vue";

export const status = ref(false);

export const errorMessage = ref("");

export const userLogin = ref("");

export const token = ref(localStorage.getItem("jwtToken" || ""));

export function setToken(newToken) {
    token.value = newToken;
    localStorage.setItem("jwtToken", newToken);
}

export function clearToken() {
    token.value = "";
    localStorage.removeItem("jwtToken");
}

export function getToken() {
    // token.value = localStorage.getItem("jwtToken");
    return localStorage.getItem("jwtToken");
}

export function isAuth() {
    return !!token.value;
}

export function useAuth() {
    return {
        token,
        setToken,
        clearToken,
        isAuth
    };
};
