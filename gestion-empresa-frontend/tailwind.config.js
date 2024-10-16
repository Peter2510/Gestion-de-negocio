/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}",],
  theme: {
    extend: {},

  },
        daisyui: {
    themes: [
      "business",
      "lofi"
    ],
  },
  plugins: [require('flowbite/plugin'), require('daisyui') ],
}

