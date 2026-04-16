/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#4F46E5",   // Indigo
        secondary: "#06B6D4", // Cyan
        dark: "#0F172A",
        light: "#F8FAFC"
      }
    },
  },
  plugins: [],
}