document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("#create-report-form");

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const csrfToken = document.querySelector("#csrf").getAttribute("value");
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    console.log(data);

    try {
      const response = await fetch("/api/v1/report", {
        method: "POST",
        headers: {
          'X-CSRF-TOKEN': csrfToken,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (response.status == 200) {
        alert("Success!");
      }
    } catch(e) {
      console.log(e);
    }
  })

  const select = form.querySelector("select");

  fetch("/api/v1/problem-type")
    .then(response => response.json())
    .then(data => {
      console.log(data);
      data.map(ptype => {
        const option = document.createElement("option");
        option.value = ptype.id;
        option.innerText = ptype.name;

        select.appendChild(option);
      })
    })
})
