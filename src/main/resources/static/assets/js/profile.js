document.addEventListener("DOMContentLoaded", () => {
  document.querySelector("#change-profile-form").addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    const csrfToken = document.querySelector("#csrf").getAttribute("value");

    try {
      const response = await fetch("/api/v1/user/profile", {
        method: 'PUT',
        headers: {
          'X-CSRF-TOKEN': csrfToken,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (response.status != 200) {
        throw "Error encountered";
      }

      const result = await response.json();
      if (result != 0) {
        alert("Success!");
      } else {
        alert("Something went wrong");
      }
    } catch(e) {
      console.warn(e)
    }
  })
})
