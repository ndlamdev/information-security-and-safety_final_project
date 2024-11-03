const icons = document.querySelectorAll(".text-editor-header>button");

icons.forEach((icon, index) => {
  icon.addEventListener("click", (e) => {
    const command = icon.dataset["element"];
    if (command == "createLink") {
      let url = prompt("Enter the link here", "http://");
      document.execCommand(command, false, url);
    } else {
      document.execCommand(command, false, null);
    }
  });
});
