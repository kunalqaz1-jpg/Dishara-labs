package com.example.data

data class QuizQuestion(
    val id: String,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val difficulty: String = "Medium" // "Easy", "Medium", "Hard"
)

object QuizProvider {

    // World 1: The Digital Universe (25 Questions)
    val world1Questions = listOf(
        // Easy (8 questions)
        QuizQuestion(
            id = "w1_q1",
            text = "What are the physical components of a computer called?",
            options = listOf("Software", "Hardware", "Malware", "Firmware"),
            correctIndex = 1,
            explanation = "Hardware refers to the physical components of a computer that you can touch, like CPU, RAM, and hard drives.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q2",
            text = "What is the full form of the IPOS model in computer fundamentals?",
            options = listOf("Input-Processing-Output-Storage", "Internet-Protocol-Output-System", "Internal-Power-Operating-Software", "Input-Program-Open-Source"),
            correctIndex = 0,
            explanation = "IPOS stands for Input, Processing, Output, and Storage, representing the four basic operations of a computer.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q3",
            text = "Which of the following is an example of software?",
            options = listOf("Keyboard", "Monitor", "Web Browser (Chrome)", "Mouse"),
            correctIndex = 2,
            explanation = "Software consists of digital programs or applications that run on hardware. Chrome is a web browser software.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q4",
            text = "A single binary digit (0 or 1) is called a:",
            options = listOf("Byte", "Bit", "Pixel", "Megabyte"),
            correctIndex = 1,
            explanation = "A 'Bit' is the smallest unit of data in computing, representing either a 0 or a 1.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q5",
            text = "How many bits make up one single Byte?",
            options = listOf("4 bits", "8 bits", "16 bits", "32 bits"),
            correctIndex = 1,
            explanation = "Exactly 8 bits are grouped together to form one Byte of data.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q6",
            text = "What is the primary role of the DNS on the internet?",
            options = listOf("To secure your password", "To translate domain names into IP addresses", "To download files faster", "To charge for internet access"),
            correctIndex = 1,
            explanation = "DNS (Domain Name System) acts like a phonebook, translating human-friendly names (like google.com) into numeric IP addresses.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q7",
            text = "In file hierarchy, the very top folder is called the ________ folder.",
            options = listOf("Branch", "Subdirectory", "Root", "Stem"),
            correctIndex = 2,
            explanation = "The top-most directory in any file system is called the 'Root' folder (usually represented by '/').",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w1_q8",
            text = "Which of these is a file extension used for digital images?",
            options = listOf(".txt", ".exe", ".jpg", ".html"),
            correctIndex = 2,
            explanation = ".jpg (or .jpeg) is a widely used file format and extension for digital photos and graphics.",
            difficulty = "Easy"
        ),
        // Medium (10 questions)
        QuizQuestion(
            id = "w1_q9",
            text = "How does internet data travel from servers to your devices?",
            options = listOf("As a single continuous beam", "Broken down into small packets", "Via satellite radio signals only", "In physical capsules"),
            correctIndex = 1,
            explanation = "Data sent over the internet is broken down into small units called packets, which travel independently and reassemble at the destination.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q10",
            text = "What binary number represents the decimal value of 5?",
            options = listOf("0101", "1100", "0011", "1010"),
            correctIndex = 0,
            explanation = "In binary, 0101 is equal to 1*4 + 0*2 + 1*1 = 5.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q11",
            text = "What is the primary objective of User Interface (UI) design?",
            options = listOf("To write complex code", "To make screens visually pleasing and easy to navigate", "To increase database security", "To compress files to a smaller size"),
            correctIndex = 1,
            explanation = "UI design focuses on creating visual elements (colors, spacing, typography) that make the app easy and inviting to use.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q12",
            text = "What are the 5 phases of the Design Thinking loop in correct sequence?",
            options = listOf(
                "Empathize -> Define -> Ideate -> Prototype -> Test",
                "Define -> Empathize -> Prototype -> Test -> Ideate",
                "Ideate -> Test -> Prototype -> Empathize -> Define",
                "Prototype -> Test -> Empathize -> Define -> Ideate"
            ),
            correctIndex = 0,
            explanation = "The canonical order is: Empathize (understand users), Define (the problem), Ideate (brainstorm), Prototype (create drafts), and Test (gather feedback).",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q13",
            text = "Why do apps like Swiggy use high-contrast action buttons?",
            options = listOf("To reduce battery consumption", "To make it harder for users to tap", "To guide user attention and trigger fast decisions", "To encrypt user transactions"),
            correctIndex = 2,
            explanation = "High-contrast buttons reduce cognitive friction, immediately drawing attention to key actions like 'Add to Cart' or 'Checkout'.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q14",
            text = "In coding logic, what is 'sequencing'?",
            options = listOf("Ordering commands step-by-step in a specific timeline", "Saving files in multiple folders", "Running multiple databases in parallel", "Connecting to the internet via router"),
            correctIndex = 0,
            explanation = "Sequencing means arranging steps or instructions in the precise order they must execute for the computer to produce the correct result.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q15",
            text = "Why do developers use 'Loops' in programming?",
            options = listOf("To make code run slower", "To avoid writing the same instruction multiple times", "To turn off the computer automatically", "To encrypt user text"),
            correctIndex = 1,
            explanation = "Loops repeat a block of code automatically, making programs more efficient, compact, and readable.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q16",
            text = "What is a 'Sprite' in block-based programming (like Scratch)?",
            options = listOf("An internet connection protocol", "An on-screen graphical character or object controlled by blocks", "A type of database storage", "A software bug in the engine"),
            correctIndex = 1,
            explanation = "In visual programming tools, a Sprite is any 2D graphic or character that can move, interact, or receive script commands.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q17",
            text = "Which of the following describes 'Empathize' in Design Thinking?",
            options = listOf("Testing the coded app", "Understanding user experiences, feelings, and constraints directly", "Writing user-interface stylesheets", "Designing database schemas"),
            correctIndex = 1,
            explanation = "Empathize involves setting aside assumptions to understand the real-world experiences, needs, and environments of the users.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w1_q18",
            text = "What is the purpose of an App Critique Project?",
            options = listOf("To copy other apps entirely", "To find usability flaws and suggest better design wireframes", "To write database queries", "To delete old code"),
            correctIndex = 1,
            explanation = "Critiquing apps helps developers identify friction points (such as poor contrast or hidden menus) to design superior user flows.",
            difficulty = "Medium"
        ),
        // Hard (7 questions)
        QuizQuestion(
            id = "w1_q19",
            text = "Which standard represents characters as binary numbers, where the letter 'A' maps to 65 (01000001)?",
            options = listOf("ASCII", "RGB", "DNS", "IPOS"),
            correctIndex = 0,
            explanation = "ASCII (American Standard Code for Information Interchange) is a character encoding standard mapping characters to binary codes.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q20",
            text = "If a repeat block is set to 'Repeat 10' and contains a nested 'Repeat 5' block inside, how many times will the innermost code execute?",
            options = listOf("15 times", "5 times", "50 times", "10 times"),
            correctIndex = 2,
            explanation = "The inner loop runs 5 times for each iteration of the outer loop. Since the outer loop runs 10 times, the total runs is 10 * 5 = 50 times.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q21",
            text = "What is the main limitation of RAM in a computer compared to hard disk storage?",
            options = listOf("RAM is volatile (loses data when powered off)", "RAM has slower read speeds", "RAM cannot store binary data", "RAM is physically larger than a hard drive"),
            correctIndex = 0,
            explanation = "RAM is temporary and volatile, meaning it requires continuous electrical power to maintain its stored information.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q22",
            text = "Which of the following coordinates user requests and manages hardware assets?",
            options = listOf("The CPU registers", "The Operating System (OS)", "The DNS phonebook", "A static wireframe"),
            correctIndex = 1,
            explanation = "The Operating System acts as the intermediate manager between software applications and the computer's physical hardware.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q23",
            text = "What color corresponds to binary RGB values of (255, 255, 255)?",
            options = listOf("Black", "White", "Pure Blue", "Bright Red"),
            correctIndex = 1,
            explanation = "Maxing out Red, Green, and Blue light channels (255, 255, 255) results in pure White light.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q24",
            text = "In design, what does the term 'Negative Space' refer to?",
            options = listOf("An error in the layout colors", "The unused empty space around and between UI elements", "The area designated for deleting data", "A button that has been clicked"),
            correctIndex = 1,
            explanation = "Negative space (or white space) is empty space surrounding UI elements, essential for readability and reducing clutter.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w1_q25",
            text = "What happens if you input a wrong IP address directly in a browser?",
            options = listOf("Your hardware crashes", "The browser cannot locate the server and returns a connection error", "It automatically repairs the network", "It opens your root file manager"),
            correctIndex = 1,
            explanation = "IP addresses identify target servers. If the IP is incorrect, the browser cannot locate the web host, failing to fetch the page.",
            difficulty = "Hard"
        )
    )

    // World 2: The Design Lab (25 Questions)
    val world2Questions = listOf(
        // Easy (8 questions)
        QuizQuestion(
            id = "w2_q1",
            text = "What is a wireframe in mobile app design?",
            options = listOf("A metal frame to hold smartphones", "A basic black-and-white sketch of on-screen elements", "A high-fidelity coded component", "A database structure map"),
            correctIndex = 1,
            explanation = "Wireframes are simple layout skeletons (usually hand-drawn in black and white) used to map user flows before adding colors and styles.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q2",
            text = "What do designers use 'Hex Codes' for?",
            options = listOf("To write logic loops", "To declare precise digital colors in styles", "To encrypt user file directories", "To measure layout dimensions in pixels"),
            correctIndex = 1,
            explanation = "Hex codes are six-character codes (e.g., #FFFFFF for white) that tell computers the precise color shade to display.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q3",
            text = "Which font style feels more serious and traditional, with small strokes at the ends of letters?",
            options = listOf("Sans-Serif", "Serif", "Monospace", "Cursive"),
            correctIndex = 1,
            explanation = "Serif fonts (like Times New Roman) have decorative feet/ticks at the ends of characters, representing a traditional, serious tone.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q4",
            text = "To ensure mobile app buttons can be easily clicked by any finger, they must have a minimum touch target size of:",
            options = listOf("12dp x 12dp", "24dp x 24dp", "48dp x 48dp", "96dp x 96dp"),
            correctIndex = 2,
            explanation = "Material Design mandates a minimum touch target of 48dp x 48dp to preserve accessibility.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q5",
            text = "What layout measurement unit is primarily used for text fonts in Android to support user-scaled text?",
            options = listOf("dp (Density-independent Pixels)", "sp (Scale-independent Pixels)", "px (Physical Pixels)", "pt (Points)"),
            correctIndex = 1,
            explanation = "Scale-independent pixels (sp) scale automatically based on the user's system font preference, ensuring text accessibility.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q6",
            text = "What is a 'User Persona'?",
            options = listOf("A security profile", "A fictional profile representing a typical target user", "An automated test script", "A system database table"),
            correctIndex = 1,
            explanation = "A user persona is a detailed archetype representing the real-world demographics, needs, and constraints of target app users.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q7",
            text = "What color is commonly chosen by trusted banking and tech apps to convey security and reliability?",
            options = listOf("Vibrant Red", "Bright Orange", "Trust Blue", "Neon Green"),
            correctIndex = 2,
            explanation = "Color psychology identifies Blue as a reliable, stable shade that naturally cultivates user trust and security.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w2_q8",
            text = "Which of the following is a key element of 'Mobile-First' design?",
            options = listOf("Designing for high-definition desktop monitors first", "Starting with small screen layouts and scaling up flexibly", "Hardcoding layouts in physical pixel columns", "Excluding all images from mobile screens"),
            correctIndex = 1,
            explanation = "Mobile-First design optimizes the layout for the tightest constraints (mobile screens) before expanding to responsive desktop sizes.",
            difficulty = "Easy"
        ),
        // Medium (10 questions)
        QuizQuestion(
            id = "w2_q9",
            text = "What does a high 'Contrast Ratio' ensure in a mobile interface?",
            options = listOf("That buttons execute commands faster", "That text is easily readable against background colors", "That files take up less storage space", "That the app doesn't require an internet connection"),
            correctIndex = 1,
            explanation = "Contrast ratios measure readability. High contrast (e.g., black text on white background) prevents eye strain and maintains visibility.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q10",
            text = "How does the '8dp spacing grid' scale help design layouts?",
            options = listOf("It automatically writes CSS selectors", "It provides a consistent scaling ratio for padding, margins, and heights", "It enforces binary logic loops", "It encrypts on-screen image files"),
            correctIndex = 1,
            explanation = "An 8dp grid uses increments of 8 (8, 16, 24, 32) to create visual alignment, rhythm, and professional spacing in interfaces.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q11",
            text = "Which navigational pattern is best suited for primary screens in a standard mobile app?",
            options = listOf("A hidden search bar", "A persistent bottom navigation bar", "A series of popup alert dialogs", "A complex folder tree directory"),
            correctIndex = 1,
            explanation = "A bottom navigation bar places crucial screens within easy reach of the user's thumb, enhancing ergonomic flow.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q12",
            text = "In prototyping, what is a 'Hotspot'?",
            options = listOf("A local Wi-Fi router share", "An interactive screen area configured to trigger screen transitions when tapped", "A thermal computer component that is overheating", "A database table that receives frequent queries"),
            correctIndex = 1,
            explanation = "In visual prototypes, hotspots are invisible clickable borders that direct the user to another mockup screen.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q13",
            text = "Why should you limit your application to a maximum of two font families?",
            options = listOf("To save database memory", "To maintain visual order, readability, and a clean typographic hierarchy", "To make background network requests faster", "To comply with internet DNS standards"),
            correctIndex = 1,
            explanation = "Too many font families create visual clutter. Sticking to two families keeps the design elegant and unified.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q14",
            text = "What is the primary benefit of paper wireframing?",
            options = listOf("It lets you write functional SQLite queries", "It allows rapid, low-cost layout ideas and testing before writing code", "It automatically generates Material 3 components", "It prevents network routing errors"),
            correctIndex = 1,
            explanation = "Paper wireframing is quick, tactile, and free. It lets you experiment and discard poor concepts in minutes.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q15",
            text = "What type of font does NOT have decorative ticks, giving it a modern, clean, and highly readable look on digital screens?",
            options = listOf("Serif", "Sans-Serif", "Old English", "Script"),
            correctIndex = 1,
            explanation = "Sans-Serif ('sans' meaning without) lacks little end-strokes, presenting clean lines ideal for readable digital screens.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q16",
            text = "How does 'Design for Accessibility' benefit users?",
            options = listOf("It makes apps cheaper to download", "It accommodates users with visual, auditory, motor, or cognitive limits", "It makes applications completely offline", "It eliminates the need to compile code"),
            correctIndex = 1,
            explanation = "Accessible design ensures that apps are fully usable by everyone, regardless of age, environment, or physical capability.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q17",
            text = "What do we call the user-journey path that models exactly how a visitor navigates through our screens?",
            options = listOf("Grid Hierarchy", "User Flow", "Box Modeling", "PWA Manifest"),
            correctIndex = 1,
            explanation = "A User Flow maps out the precise, step-by-step route a user takes from opening the app to completing a goal.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w2_q18",
            text = "In Material 3, which button variant is best for secondary, less important actions?",
            options = listOf("Filled Button", "Elevated Button", "Outlined Button", "Floating Action Button"),
            correctIndex = 2,
            explanation = "Outlined buttons have a visible border but no fill, designating them as secondary choices compared to high-emphasis filled buttons.",
            difficulty = "Medium"
        ),
        // Hard (7 questions)
        QuizQuestion(
            id = "w2_q19",
            text = "If a tablet screen is 1200dp wide, what responsive strategy prevents text lines from stretching awkwardly?",
            options = listOf("Scale the font size to 48sp", "Place content in centered container with width limits (e.g., maxWidth = 600dp)", "Cover half the screen with static black boxes", "Force the tablet to run in portrait mode only"),
            correctIndex = 1,
            explanation = "Limiting width and centering the content on wider screens keeps paragraph line-lengths comfortable and visually pleasing.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q20",
            text = "What does the 'Ripple Effect' provide in Material Design button interaction?",
            options = listOf("It encrypts user clicks", "It gives instant visual feedback acknowledging that a touch target has been tapped", "It allows the app to scroll horizontally", "It establishes a persistent database hook"),
            correctIndex = 1,
            explanation = "Ripples are interactive feedback cues. They show exactly where the user touched the component to reduce second-guessing.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q21",
            text = "Which of the following describes a 'Clickable Prototype'?",
            options = listOf("A fully functional Kotlin application", "Static design frames joined together via hotspot linkages simulating interaction", "A database layout map", "A linter execution terminal"),
            correctIndex = 1,
            explanation = "A prototype simulates flow and transitions by routing user actions on hotspot mockups without full database logic.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q22",
            text = "In design psychology, why does red color spark immediate action in meal ordering apps?",
            options = listOf("Red light takes less memory to render", "Red color stimulates hunger, energy, and visual urgency", "Red is the default color of Android icons", "Red prevents the screen from going to sleep"),
            correctIndex = 1,
            explanation = "Red is a psychological stimulant. It triggers feelings of hunger, promptness, and high energy.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q23",
            text = "What is the primary objective of a wireframe's crossing lines inside a rectangle (an X-box)?",
            options = listOf("To represent an input field", "To designate a placeholder where a graphic or image belongs", "To show a broken component", "To mark a database deletion record"),
            correctIndex = 1,
            explanation = "In wireframe drawings, a rectangle containing an internal 'X' denotes a visual placeholder for pictures or banners.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q24",
            text = "What is the danger of hardcoding fixed pixel values (like width = 360px) in mobile layouts?",
            options = listOf("The app fails to build on Gradle", "The layout breaks, overflows, or clips awkwardly on varying screen sizes", "It requires constant network connection", "It resets user streak counters"),
            correctIndex = 1,
            explanation = "Physical widths are rigid. Hardcoded values fail to scale on larger, smaller, or foldable screens.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w2_q25",
            text = "In accessibility guidelines, what is the minimum contrast ratio required for standard body text?",
            options = listOf("1:1", "3:1", "4.5:1", "10:1"),
            correctIndex = 2,
            explanation = "WCAG AA guidelines mandate a minimum contrast ratio of 4.5:1 for standard body text to support users with low vision.",
            difficulty = "Hard"
        )
    )

    // World 3: The Web Builder (25 Questions)
    val world3Questions = listOf(
        // Easy (8 questions)
        QuizQuestion(
            id = "w3_q1",
            text = "What does HTML stand for?",
            options = listOf("HyperText Markup Language", "HighTech Machine Learning", "Home Tool Markup Layout", "Hyperlink Transfer Management Logic"),
            correctIndex = 0,
            explanation = "HTML stands for HyperText Markup Language, the standard structure of web documents.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q2",
            text = "Which HTML tag is used to create a standard clickable hyperlink?",
            options = listOf("<link>", "<a>", "<href>", "<button>"),
            correctIndex = 1,
            explanation = "The anchor tag <a> with its 'href' attribute is used to link to other webpages.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q3",
            text = "Which tag is used to define the largest heading on an HTML page?",
            options = listOf("<h6>", "<head>", "<h1>", "<heading>"),
            correctIndex = 2,
            explanation = "<h1> defines the most important and largest heading, down to <h6> for the smallest.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q4",
            text = "What is the primary role of CSS in web development?",
            options = listOf("To handle database backups", "To style, format, and layout HTML structures", "To resolve server routing errors", "To compile visual block algorithms"),
            correctIndex = 1,
            explanation = "CSS (Cascading Style Sheets) controls the look, feel, color, layout, and fonts of a webpage.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q5",
            text = "Which CSS property is used to change the background color of an element?",
            options = listOf("color", "text-color", "background-color", "style-background"),
            correctIndex = 2,
            explanation = "The 'background-color' property sets the background color of HTML elements.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q6",
            text = "What is JavaScript used for in front-end development?",
            options = listOf("To model physical servers", "To add interactivity, behavior, and logic to static webpages", "To sketch paper prototypes", "To host domain addresses"),
            correctIndex = 1,
            explanation = "JavaScript is a scripting language that allows you to make websites dynamic and responsive to clicks, inputs, and events.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q7",
            text = "Which HTML tag is used to insert an image?",
            options = listOf("<picture>", "<src>", "<img>", "<image>"),
            correctIndex = 2,
            explanation = "The <img> tag embeds images on webpages using the 'src' attribute.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w3_q8",
            text = "In CSS Box Model, what space is directly between the content and the border?",
            options = listOf("Margin", "Padding", "Spacer", "Outline"),
            correctIndex = 1,
            explanation = "Padding is the internal spacing inside an element, sitting between the core content and its border.",
            difficulty = "Easy"
        ),
        // Medium (10 questions)
        QuizQuestion(
            id = "w3_q9",
            text = "What is the correct syntax to connect an anchor tag <a> to google.com?",
            options = listOf(
                "<a src=\"https://google.com\">Google</a>",
                "<a href=\"https://google.com\">Google</a>",
                "<a link=\"https://google.com\">Google</a>",
                "<anchor path=\"https://google.com\">Google</anchor>"
            ),
            correctIndex = 1,
            explanation = "The 'href' (hypertext reference) attribute is used by anchor tags to define the destination URL.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q10",
            text = "What is the key difference between <ul> and <ol> list tags?",
            options = listOf(
                "<ul> makes bulleted lists, while <ol> makes numbered lists",
                "<ul> is only used for images, <ol> for links",
                "<ul> is styled with CSS, <ol> cannot be styled",
                "<ul> is a database table, <ol> is a browser class"
            ),
            correctIndex = 0,
            explanation = "<ul> is an Unordered (bulleted) List, while <ol> is an Ordered (numbered) List.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q11",
            text = "In CSS Box Model, what does the 'Margin' property control?",
            options = listOf("The color of the border line", "The outer spacing pushing adjacent elements away", "The thickness of internal shadows", "The text alignment"),
            correctIndex = 1,
            explanation = "Margin represents the empty space around the outside of an element's border, pushing neighboring items away.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q12",
            text = "How do you align items inside a Flexbox container along the main horizontal axis?",
            options = listOf("text-align: center;", "justify-content", "align-items", "box-direction"),
            correctIndex = 1,
            explanation = "'justify-content' aligns Flexbox child elements along the main axis (usually horizontal).",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q13",
            text = "How do you register a button click listener in JavaScript?",
            options = listOf(
                "button.onClick = execute()",
                "button.addEventListener('click', () => { })",
                "button.listenToTap(event)",
                "button.trigger('click')"
            ),
            correctIndex = 1,
            explanation = "Using 'addEventListener' is the standard, modern approach to handle interactive user events in JS.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q14",
            text = "Which tag defines structural boundaries containing primary navigation links?",
            options = listOf("<nav>", "<main>", "<header>", "<footer>"),
            correctIndex = 0,
            explanation = "The semantic <nav> tag specifies a section of navigation links to improve document structure and screen readers.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q15",
            text = "What is the CSS selector used to match all paragraph elements on a page?",
            options = listOf(".paragraph", "#p", "p", "*p"),
            correctIndex = 2,
            explanation = "An element/tag selector (like 'p') targets all corresponding HTML tags on a page directly.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q16",
            text = "In HTML, which attribute is essential for search engines and screen readers on an <img> tag?",
            options = listOf("href", "alt", "title", "class"),
            correctIndex = 1,
            explanation = "The 'alt' (alternative text) attribute describes the image content for screen readers and search engines.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q17",
            text = "How do you select an element with an id of 'submit-btn' in CSS?",
            options = listOf(".submit-btn", "#submit-btn", "submit-btn", "*submit-btn"),
            correctIndex = 1,
            explanation = "ID selectors in CSS are prefixed with a hash character '#'. Class selectors use a dot '.'.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w3_q18",
            text = "What is the purpose of the classList.toggle() method in JavaScript?",
            options = listOf(
                "To delete the element",
                "To add a class if it is missing, or remove it if it exists",
                "To send an API request",
                "To create a new HTML tag"
            ),
            correctIndex = 1,
            explanation = "classList.toggle() simplifies switching styling classes (such as adding or removing a '.dark' theme class).",
            difficulty = "Medium"
        ),
        // Hard (7 questions)
        QuizQuestion(
            id = "w3_q19",
            text = "If an element has 'padding: 10px 20px 5px 15px;', what is the padding on the LEFT side?",
            options = listOf("10px", "20px", "5px", "15px"),
            correctIndex = 3,
            explanation = "CSS shorthand values rotate clockwise: Top (10px), Right (20px), Bottom (5px), and Left (15px).",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q20",
            text = "What is the primary difference between a block element (like <div>) and an inline element (like <span>)?",
            options = listOf(
                "Block elements start on a new line and take full available width; inline elements do not",
                "Inline elements cannot be styled with CSS",
                "Block elements are used for database operations only",
                "Inline elements automatically trigger dark mode"
            ),
            correctIndex = 0,
            explanation = "Block elements create a box break, consuming the full available layout width. Inline elements sit directly inside text flows.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q21",
            text = "Which CSS declaration makes a container align its children horizontally and scale responsively?",
            options = listOf("display: block;", "display: inline;", "display: flex;", "display: inline-block;"),
            correctIndex = 2,
            explanation = "Setting 'display: flex;' activates Flexbox layout controls, enabling flexible sizing of interior components.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q22",
            text = "What tag structure is used to create a multi-level list representing folders?",
            options = listOf("<list><item></list>", "Nested list elements (like a <ul> inside a <li> element)", "<ol><p></ol>", "<span><div></span>"),
            correctIndex = 1,
            explanation = "Nesting list elements creates a hierarchical, tree-like structure perfect for modeling directories.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q23",
            text = "In CSS styling, which selector has the highest priority/specificity?",
            options = listOf("Tag selector (e.g., p)", "Class selector (e.g., .card)", "ID selector (e.g., #header)", "Universal selector (e.g., *)"),
            correctIndex = 2,
            explanation = "ID selectors have higher specificity than classes, tags, or universal selectors, overriding their styles.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q24",
            text = "What is semantic HTML?",
            options = listOf(
                "HTML that is written automatically by AI",
                "HTML tags that explicitly describe their meaning and role to browsers and assistive technologies",
                "HTML code that runs completely offline",
                "HTML sheets styled using only hex colors"
            ),
            correctIndex = 1,
            explanation = "Semantic HTML tags (like <header>, <main>, <nav>) describe their structural content clearly, improving SEO and screen reader navigation.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w3_q25",
            text = "What happens if a JavaScript file contains a syntax error?",
            options = listOf(
                "The browser crashes the operating system",
                "The browser stops executing scripts, usually reporting an error in the console",
                "The HTML structure is deleted",
                "The web server shuts down"
            ),
            correctIndex = 1,
            explanation = "Syntax errors prevent JS engines from parsing instructions, causing scripts to fail and output error details in the browser console.",
            difficulty = "Hard"
        )
    )

    // World 4: The Logician (25 Questions)
    val world4Questions = listOf(
        // Easy (8 questions)
        QuizQuestion(
            id = "w4_q1",
            text = "In coding, what is a variable?",
            options = listOf(
                "A computer virus",
                "A named container in memory used to store data values",
                "A physical device inside a computer",
                "A button that users can click"
            ),
            correctIndex = 1,
            explanation = "Variables are named storage boxes in computer memory where data is saved, retrieved, and changed.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q2",
            text = "Which keyword is used to declare a variable that CANNOT be reassigned later?",
            options = listOf("let", "const", "var", "fixed"),
            correctIndex = 1,
            explanation = "The 'const' keyword creates a constant variable whose value cannot be changed or reassigned after definition.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q3",
            text = "What are the only two possible values of a Boolean data type?",
            options = listOf("1 and 2", "true and false", "high and low", "yes and no"),
            correctIndex = 1,
            explanation = "A Boolean represents a binary logic state, which can only be 'true' or 'false'.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q4",
            text = "Which operator is used to multiply two numbers in code?",
            options = listOf("x", "*", "+", "/"),
            correctIndex = 1,
            explanation = "The asterisk '*' is the standard symbol used for arithmetic multiplication in programming languages.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q5",
            text = "What does an 'if-else' statement allow a program to do?",
            options = listOf(
                "Repeat an action 10 times",
                "Make decisions and execute different code blocks based on conditions",
                "Save records into a persistent database",
                "Format CSS backgrounds"
            ),
            correctIndex = 1,
            explanation = "If-else statements branch execution paths. If a condition is true, one block runs; otherwise, the alternative else block runs.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q6",
            text = "In programming, what is a function?",
            options = listOf(
                "A database connection profile",
                "A reusable block of packaged code designed to perform a specific task",
                "A software error",
                "A responsive CSS grid container"
            ),
            correctIndex = 1,
            explanation = "Functions are modular, self-contained packages of logic that can be executed repeatedly by calling their name.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q7",
            text = "What is an 'Array' or 'List' in coding?",
            options = listOf(
                "A single variable that holds an ordered collection of multiple items",
                "A style file that changes colors",
                "A secure password encryption key",
                "A type of network router"
            ),
            correctIndex = 0,
            explanation = "Arrays store multiple values in a single ordered variable, accessible via indices starting from 0.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w4_q8",
            text = "What does the term 'API' stand for?",
            options = listOf(
                "Application Programming Interface",
                "Algorithmic Performance Index",
                "Automated Processing Integration",
                "Access Point Internet"
            ),
            correctIndex = 0,
            explanation = "API stands for Application Programming Interface, allowing different software programs to communicate and share data.",
            difficulty = "Easy"
        ),
        // Medium (10 questions)
        QuizQuestion(
            id = "w4_q9",
            text = "In JavaScript, how do you add a new item to the very end of an Array?",
            options = listOf("list.addEnd()", "list.push()", "list.append()", "list.insert()"),
            correctIndex = 1,
            explanation = "The '.push()' method appends one or more items onto the end of an array and returns its new size.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q10",
            text = "If an array has 5 items, what is the index number of the first item?",
            options = listOf("1", "0", "-1", "First"),
            correctIndex = 1,
            explanation = "Most computer languages use zero-based indexing, meaning the initial item sits at index 0.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q11",
            text = "What does the 'return' keyword do inside a function?",
            options = listOf(
                "It loops back to the start of the program",
                "It stops executing the function and outputs a value back to where it was called",
                "It deletes the variables inside memory",
                "It navigates back to the home screen"
            ),
            correctIndex = 1,
            explanation = "Return stops execution and sends the calculated result back to the code that invoked the function.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q12",
            text = "Which comparison operator checks if two values are equal in both value and data type?",
            options = listOf("=", "==", "===", "!="),
            correctIndex = 2,
            explanation = "In modern scripting, '===' is the strict equality operator checking both matching values and identical types.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q13",
            text = "What format is commonly used to transfer data packets between APIs and web applications?",
            options = listOf("HTML text", "JSON (JavaScript Object Notation)", "binary binary codes", "ZIP folders"),
            correctIndex = 1,
            explanation = "JSON is a lightweight, human-readable text format used universally to represent structured data objects in APIs.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q14",
            text = "Which function call is used in JavaScript to fetch data from an online API server?",
            options = listOf("fetch()", "retrieveAPI()", "getDatabase()", "unpackJSON()"),
            correctIndex = 0,
            explanation = "The global 'fetch()' method initiates asynchronous network requests to retrieve API data.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q15",
            text = "What is the result of the arithmetic expression: 15 % 4?",
            options = listOf("3", "1", "2", "0"),
            correctIndex = 0,
            explanation = "The modulo '%' operator calculates the remainder of division. 15 divided by 4 is 3, with a remainder of 3.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q16",
            text = "What is an infinite loop?",
            options = listOf(
                "A loop that executes exactly 100 times",
                "A loop whose termination condition is never met, causing it to run forever",
                "A recursive folder directory",
                "A loop that has been deleted"
            ),
            correctIndex = 1,
            explanation = "If a loop's condition remains true indefinitely, it loops forever, causing memory exhaustion and browser freezing.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q17",
            text = "Why do we pass 'parameters' or 'arguments' into functions?",
            options = listOf(
                "To make the code harder to read",
                "To provide input values for the function to process dynamic tasks",
                "To encrypt security logs",
                "To declare global variables"
            ),
            correctIndex = 1,
            explanation = "Parameters act as placeholders, letting functions receive custom inputs and operate dynamically.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w4_q18",
            text = "What does the array property '.length' return?",
            options = listOf("The byte size of the list", "The total number of elements inside the array", "The index of the last item", "The style height"),
            correctIndex = 1,
            explanation = "The length property counts exactly how many elements are stored in that array.",
            difficulty = "Medium"
        ),
        // Hard (7 questions)
        QuizQuestion(
            id = "w4_q19",
            text = "Consider: let x = 10; x += 5; x *= 2; What is the final value of x?",
            options = listOf("20", "30", "15", "25"),
            correctIndex = 1,
            explanation = "First: x becomes 10 + 5 = 15. Then: x is multiplied by 2, yielding 15 * 2 = 30.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q20",
            text = "How does asynchronous fetch handle network wait times in JavaScript?",
            options = listOf(
                "It pauses the computer until the server responds",
                "It uses promises or async/await to execute other lines while waiting in the background",
                "It returns a syntax error instantly",
                "It restarts the app"
            ),
            correctIndex = 1,
            explanation = "Asynchronous calls use Promises or async/await to fetch data in the background, preventing the UI from freezing.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q21",
            text = "In logic operations, what is the output of: (true && false) || (true || false)?",
            options = listOf("true", "false", "undefined", "null"),
            correctIndex = 0,
            explanation = "(true && false) yields false. (true || false) yields true. Finally, false || true resolves to true.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q22",
            text = "What will happen if you reference an array index that does not exist (like list[10] on an array of size 3)?",
            options = listOf(
                "The computer catches fire",
                "It returns undefined or throws an out-of-bounds error depending on the language",
                "It automatically resizes the list",
                "It resets the variable to zero"
            ),
            correctIndex = 1,
            explanation = "Accessing unallocated array indexes returns undefined in JS, or throws an index out-of-bounds exception in Kotlin.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q23",
            text = "What does JSON.parse() do in JavaScript?",
            options = listOf(
                "Converts a JavaScript object into a JSON string",
                "Parses a JSON-formatted string into a working JavaScript object",
                "Sends a network fetch query",
                "Deletes string quotation marks"
            ),
            correctIndex = 1,
            explanation = "JSON.parse() unpacks string data received from API servers, converting it into a structured object.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q24",
            text = "What represents a nested conditional statement?",
            options = listOf(
                "An if-statement placed inside another if-statement block",
                "A loop that has no end",
                "Two CSS style properties declared together",
                "A variable with two distinct names"
            ),
            correctIndex = 0,
            explanation = "Nesting conditionals places an inner 'if' check inside an outer 'if' block, evaluating fine-grained sequential rules.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w4_q25",
            text = "If a function does not contain a return statement, what value does it return by default in JavaScript?",
            options = listOf("0", "null", "undefined", "false"),
            correctIndex = 2,
            explanation = "In JavaScript, if a function finishes executing without hitting a 'return' statement, it implicitly returns 'undefined'.",
            difficulty = "Hard"
        )
    )

    // World 5: The Maker (25 Questions)
    val world5Questions = listOf(
        // Easy (8 questions)
        QuizQuestion(
            id = "w5_q1",
            text = "What is the primary benefit of a database over local variables?",
            options = listOf(
                "Databases run code faster",
                "Databases persist data safely across device reboots and app sessions",
                "Databases don't require any code syntax",
                "Databases make layouts look better"
            ),
            correctIndex = 1,
            explanation = "Variables lose data on app exit. Databases act as permanent locker systems, preserving state indefinitely.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q2",
            text = "What does CRUD stand for in database operations?",
            options = listOf(
                "Create, Read, Update, Delete",
                "Compile, Run, Undo, Debug",
                "Cloud, Routing, User, Dashboard",
                "Commit, Retrieve, Upload, Deploy"
            ),
            correctIndex = 0,
            explanation = "CRUD represents the four basic operations of persistent storage: Create, Read, Update, and Delete.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q3",
            text = "Which database action corresponds to modifying an existing record's status?",
            options = listOf("Create", "Read", "Update", "Delete"),
            correctIndex = 2,
            explanation = "'Update' refers to altering data fields on an already existing record in the database.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q4",
            text = "What is 'Authentication' inside mobile applications?",
            options = listOf(
                "Compressing image resolutions",
                "Verifying user identities (e.g. sign up, log in) to secure private spaces",
                "Compiling Gradle build configurations",
                "Checking for physical screen cracks"
            ),
            correctIndex = 1,
            explanation = "Authentication verifies that users are who they claim to be, granting secure access to private profiles.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q5",
            text = "What is debugging in software development?",
            options = listOf(
                "Removing real insects from keyboard slots",
                "Finding and fixing logical or syntax errors in source code",
                "Deploying apps to the cloud",
                "Designing the login screen layout"
            ),
            correctIndex = 1,
            explanation = "Debugging is the systematic process of finding, isolating, and resolving defects or 'bugs' in a program.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q6",
            text = "What does 'Cloud Deployment' mean?",
            options = listOf(
                "Uploading software to real weather balloons",
                "Publishing application files on internet-accessible cloud hosting servers",
                "Compressing code into backup zip archives",
                "Running apps completely offline"
            ),
            correctIndex = 1,
            explanation = "Deployment hosts files on live internet web servers, assigning public URLs so anyone can access them globally.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q7",
            text = "What does 'PWA' stand for in web apps?",
            options = listOf(
                "Progressive Web Application",
                "Private Wireless Access",
                "Protocol Web Accelerator",
                "Personal Work Assistant"
            ),
            correctIndex = 0,
            explanation = "PWA stands for Progressive Web App, websites configured with manifest files to install and feel like native mobile apps.",
            difficulty = "Easy"
        ),
        QuizQuestion(
            id = "w5_q8",
            text = "Which try-catch block segment contains code that executes ONLY when an exception/error occurs?",
            options = listOf("try block", "catch block", "finally block", "throw block"),
            correctIndex = 1,
            explanation = "The 'catch' block contains recovery code that triggers exclusively if an error is thrown inside the preceding 'try' block.",
            difficulty = "Easy"
        ),
        // Medium (10 questions)
        QuizQuestion(
            id = "w5_q9",
            text = "Which of the following SQL statements inserts a new task record?",
            options = listOf(
                "UPDATE tasks SET status = 1",
                "INSERT INTO tasks (title, status) VALUES ('Do homework', 0)",
                "SELECT * FROM tasks",
                "DELETE FROM tasks WHERE id = 1"
            ),
            correctIndex = 1,
            explanation = "The SQL 'INSERT INTO' clause is used to Create and insert new data rows into tables.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q10",
            text = "What is the role of password hashing in secure authentication?",
            options = listOf(
                "It makes passwords shorter to save database rows",
                "It encrypts passwords into unreadable hashes to protect them from theft",
                "It automatically logs users out on tab close",
                "It lets users login without typing text"
            ),
            correctIndex = 1,
            explanation = "Hashing converts raw passwords into secure, irreversible cryptographic strings, protecting credentials from leaks.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q11",
            text = "What is the primary benefit of 'Snapshot Listeners' in database integrations?",
            options = listOf(
                "They capture on-device screenshots automatically",
                "They trigger real-time screen updates as soon as records change in the database",
                "They slow down network downloads to save bandwidth",
                "They debug syntax errors automatically"
            ),
            correctIndex = 1,
            explanation = "Snapshot listeners continuously monitor databases, letting frontends immediately display updates without manual page refreshes.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q12",
            text = "What is beta testing?",
            options = listOf(
                "Running final tests inside a computer lab only",
                "Releasing your app to a limited group of real users to collect feedback and spot bugs",
                "Using beta versions of code editors to compile fast",
                "An automated script that deletes database tables"
            ),
            correctIndex = 1,
            explanation = "Beta testing collects real-world telemetry and feedback from external users before full public launching.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q13",
            text = "What does the console.log() method help web developers accomplish?",
            options = listOf(
                "It designs a visual navigation drawer",
                "It outputs diagnostic variables and messages to the browser inspection console",
                "It secures database collection tables",
                "It installs local web browser applications"
            ),
            correctIndex = 1,
            explanation = "console.log() prints variable states and trace information, assisting developers in troubleshooting logical failures.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q14",
            text = "Which cloud hosts are popular for rapid, easy frontend web page deployments?",
            options = listOf("Vercel & Netlify", "MySQL & SQLite", "Android Studio & VS Code", "Figma & Photoshop"),
            correctIndex = 0,
            explanation = "Vercel and Netlify are popular, automated cloud hosting platforms optimized for static and responsive web applications.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q15",
            text = "In PWA development, what does the 'manifest.json' file specify?",
            options = listOf(
                "The server's secure API keys",
                "The app's installable icons, launch theme colors, and stand-alone desktop layout parameters",
                "A list of SQL table schemas",
                "The source code compiler instructions"
            ),
            correctIndex = 1,
            explanation = "The web app manifest defines configuration settings that tell mobile devices how to handle native installations.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q16",
            text = "Why do we add soft elevations (drop shadows) or loading spinners to refined interfaces?",
            options = listOf(
                "To increase download speeds",
                "To elevate visual cues, feedback states, and overall professional quality",
                "To encrypt backend API requests",
                "To bypass user login screens"
            ),
            correctIndex = 1,
            explanation = "Polishing visual elements helps guide eyes, hints at clickable areas, and maintains a clean, premium experience.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q17",
            text = "What happens if a database transaction fails halfway?",
            options = listOf(
                "The database is permanently corrupted",
                "It rolls back, undoing any partial changes to preserve clean data state",
                "It deletes the user's password",
                "The compiler displays an out-of-bounds warning"
            ),
            correctIndex = 1,
            explanation = "Database systems enforce atomicity, meaning transactions either complete fully or roll back completely, preventing messy half-saved records.",
            difficulty = "Medium"
        ),
        QuizQuestion(
            id = "w5_q18",
            text = "Which keyword is used to actively throw a custom exception/error in your code logic?",
            options = listOf("catch", "try", "throw", "error"),
            correctIndex = 2,
            explanation = "The 'throw' statement interrupts normal program execution, passing a custom error object to catch handlers.",
            difficulty = "Medium"
        ),
        // Hard (7 questions)
        QuizQuestion(
            id = "w5_q19",
            text = "In a secure system, why is storing plain-text user passwords inside databases considered a critical failure?",
            options = listOf(
                "Plain-text passwords take up too much database space",
                "If the database is leaked, hackers immediately see every user's secret password",
                "Plain-text variables prevent the linter from building code",
                "Database tables refuse to read unencrypted files"
            ),
            correctIndex = 1,
            explanation = "If intruders access database entries, plain-text records expose logins directly. Hashing keeps passwords secure even during data breaches.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q20",
            text = "In Room Database for Android, what does falling back to 'Destructive Migration' do when you update database version numbers?",
            options = listOf(
                "It deletes the entire app code",
                "It wipes all existing user data tables clean and recreates fresh structures from scratch",
                "It throws a compile-time Kotlin exception",
                "It syncs data automatically to external server databases"
            ),
            correctIndex = 1,
            explanation = "Destructive migration is simple and quick during development. It drops existing tables and recreates them, erasing local history to apply schema edits.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q21",
            text = "What is the primary role of a service worker in a Progressive Web App (PWA)?",
            options = listOf(
                "To structure HTML layout grids",
                "To intercept network calls, cache pages, and enable complete offline capabilities",
                "To encrypt passwords inside database files",
                "To execute visual coding block sequencing"
            ),
            correctIndex = 1,
            explanation = "Service workers are background scripts running inside browsers, acting as local proxies that intercept calls and serve cached assets offline.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q22",
            text = "What is the danger of leaving old console logs or detailed debugging outputs inside your public production release?",
            options = listOf(
                "The web server will delete your site",
                "It leaks sensitive state variables, API routes, or tokens to tech-savvy users inspecting the browser",
                "It forces mobile devices to run hot",
                "It disables navigation bottom bars"
            ),
            correctIndex = 1,
            explanation = "Production logs reveal architectural patterns, parameters, or private access tokens to inspectors. Developers strip logs before launching.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q23",
            text = "Which of the following represents a full capstone project checklist for a student creator?",
            options = listOf(
                "Figma draft -> Paper notes -> Video sketch",
                "Secure Auth -> CRUD data persistence -> Responsive UI layouts -> Active Cloud URL deployment",
                "Single text header -> Red button -> DNS domain name",
                "Local variables let -> Boolean flags -> HTML block"
            ),
            correctIndex = 1,
            explanation = "A complete capstone project models real software: secure registration, database operations, friendly visual styles, and public cloud hosting.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q24",
            text = "How does local data persistence (like Room) differ from cloud storage (like Supabase or Firebase)?",
            options = listOf(
                "Room cannot store text strings",
                "Room stores records on-device (offline-first); cloud stores them on remote servers across the internet",
                "Cloud storage cannot save user passwords",
                "Room requires a constant Wi-Fi connection"
            ),
            correctIndex = 1,
            explanation = "Local persistence saves data locally inside the device's internal vault, which is fully accessible without network access.",
            difficulty = "Hard"
        ),
        QuizQuestion(
            id = "w5_q25",
            text = "If you run an app with a broken database query (e.g. referencing a non-existent table) without try-catch wraps, what occurs?",
            options = listOf(
                "The database query is ignored and the app runs normally",
                "The application throws a runtime crash and shuts down immediately",
                "The table is automatically created by the operating system",
                "The phone restarts automatically"
            ),
            correctIndex = 1,
            explanation = "Unhandled database exceptions disrupt program execution, causing the OS to terminate the app process (a crash).",
            difficulty = "Hard"
        )
    )

    // Helper to get questions for a specific World Index (1 to 5)
    fun getQuestionsForWorld(worldIndex: Int): List<QuizQuestion> {
        return when (worldIndex) {
            1 -> world1Questions
            2 -> world2Questions
            3 -> world3Questions
            4 -> world4Questions
            5 -> world5Questions
            else -> world1Questions
        }
    }

    // Helper to generate a mini-quiz (5 questions) for a specific lesson id
    fun getMiniQuizForLesson(lessonId: String): List<QuizQuestion> {
        // Extract world from lessonId (e.g., "lvl_1_3" -> World 1, "lvl_4_2" -> World 4)
        val parts = lessonId.split("_")
        val world = if (parts.size >= 3) parts[1].toIntOrNull() ?: 1 else 1
        val levelIndex = if (parts.size >= 3) parts[2].toIntOrNull() ?: 1 else 1

        val allWorldQuestions = getQuestionsForWorld(world)

        // Select 5 questions from the world questions pool.
        // To make it relevant to the level index, let's select 5 questions deterministically.
        val startIndex = ((levelIndex - 1) * 2) % (allWorldQuestions.size - 5)
        return allWorldQuestions.subList(startIndex, startIndex + 5)
    }
}
