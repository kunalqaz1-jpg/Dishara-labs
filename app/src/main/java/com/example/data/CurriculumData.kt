package com.example.data

data class CurriculumLesson(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val subtitleEn: String,
    val subtitleHi: String,
    val durationMin: Int,
    val readingEn: String,
    val readingHi: String,
    val codeSnippet: String = "",
    val keyTerms: List<String> = emptyList(),
    val difficulty: String = "Beginner"
)

data class CurriculumModule(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val category: String, // Coding, Design, Career, Soft Skills
    val iconName: String, // "code", "brush", "work", "school"
    val lessons: List<CurriculumLesson>
)

object CurriculumProvider {

    private val world1Lessons = listOf(
        CurriculumLesson(
            id = "lvl_1_1",
            titleEn = "How Does This Magic Box Work?",
            titleHi = "यह जादुई बॉक्स कैसे काम करता है?",
            subtitleEn = "Level 1.1 • Computer Fundamentals",
            subtitleHi = "लेवल 1.1 • कंप्यूटर फंडामेंटल्स",
            durationMin = 15,
            readingEn = "A computer is a general-purpose problem-solving machine. It consists of Hardware (physical components like CPU, RAM) and Software (digital apps). Every computer follows the Input-Processing-Output-Storage (IPOS) model.",
            readingHi = "कंप्यूटर एक समस्या-समाधान मशीन है। इसमें हार्डवेयर (CPU, RAM) और सॉफ्टवेयर (निर्देश) शामिल हैं। सभी कंप्यूटर इनपुट-प्रोसेसिंग-आउटपुट-स्टोरेज (IPOS) मॉडल पर काम करते हैं।",
            codeSnippet = "Input -> Processing (CPU) -> Output -> Storage",
            keyTerms = listOf("Hardware", "Software", "IPOS Model")
        ),
        CurriculumLesson(
            id = "lvl_1_2",
            titleEn = "The Internet Is Just a Postman",
            titleHi = "इंटरनेट सिर्फ एक डाकिया है",
            subtitleEn = "Level 1.2 • Networking Basics",
            subtitleHi = "लेवल 1.2 • नेटवर्किंग बेसिक्स",
            durationMin = 20,
            readingEn = "The internet is a physical network of connected computers. When you request a webpage, data travels in Packets. IP Addresses identify servers, while DNS acts as the phonebook translating URLs into numeric IPs.",
            readingHi = "इंटरनेट आपस में जुड़े कंप्यूटरों का एक भौतिक नेटवर्क है। जब आप अनुरोध करते हैं, तो डेटा पैकेट में यात्रा करता है। आईपी पते सर्वर की पहचान करते हैं, और DNS फोनबुक का काम करता है।",
            codeSnippet = "Browser -> DNS Lookup -> Fetch Webpage from Server",
            keyTerms = listOf("Packets", "IP Address", "DNS")
        ),
        CurriculumLesson(
            id = "lvl_1_3",
            titleEn = "Your Phone's Secret Language",
            titleHi = "आपके फोन की गुप्त भाषा",
            subtitleEn = "Level 1.3 • Binary Code",
            subtitleHi = "लेवल 1.3 • बाइनरी कोड",
            durationMin = 20,
            readingEn = "Computers only understand ON (1) or OFF (0), known as Binary. A single 1/0 is a Bit, and 8 bits form a Byte. ASCII and RGB map characters and colors into binary ones and zeros.",
            readingHi = "कंप्यूटर केवल चालू (1) या बंद (0) को समझते हैं, जिसे बाइनरी कहा जाता है। एक 1/0 बिट (Bit) है, और 8 बिट्स मिलकर बाइट (Byte) बनाते हैं।",
            codeSnippet = "Letter 'A' = 01000001 (65)\nRGB (255,255,255) = White",
            keyTerms = listOf("Binary", "Bits & Bytes", "ASCII")
        ),
        CurriculumLesson(
            id = "lvl_1_4",
            titleEn = "The Great File Hunt",
            titleHi = "महान फ़ाइल खोज",
            subtitleEn = "Level 1.4 • File Management",
            subtitleHi = "लेवल 1.4 • फ़ाइल प्रबंधन",
            durationMin = 15,
            readingEn = "Files store digital work with specific extensions (like .txt, .jpg, .html). We organize files inside folders (directories) arranged in a neat tree hierarchy, which is essential for developers.",
            readingHi = "फ़ाइलें विशिष्ट एक्सटेंशन (.txt, .jpg, .html) के साथ काम सहेजती हैं। हम फ़ाइलों को फ़ोल्डर्स (डायरेक्टरीज़) में एक पदानुक्रम में व्यवस्थित करते हैं।",
            codeSnippet = "Root /\n  ├── Documents/\n  └── Pictures/",
            keyTerms = listOf("File Extensions", "Directories", "Hierarchy")
        ),
        CurriculumLesson(
            id = "lvl_1_5",
            titleEn = "Why Does Instagram Look So Good?",
            titleHi = "इंस्टाग्राम इतना अच्छा क्यों दिखता है?",
            subtitleEn = "Level 1.5 • Intro to Design",
            subtitleHi = "लेवल 1.5 • डिजाइन का परिचय",
            durationMin = 20,
            readingEn = "Great apps succeed because of design. Good UI uses pleasing colors, readable typography, and balanced spacing (negative space) to reduce cognitive load and guide attention to main actions.",
            readingHi = "महान ऐप्स सुंदर डिज़ाइन के कारण सफल होते हैं। अच्छा यूआई रंगों, टाइपोग्राफी और संतुलित स्पेसिंग का उपयोग करके ध्यान आकर्षित करता है।",
            codeSnippet = "Visual: Headline (Bold) -> Action (Bright Accent) -> Background (Neutral)",
            keyTerms = listOf("UI/UX Design", "Typography", "Spacing Grid")
        ),
        CurriculumLesson(
            id = "lvl_1_6",
            titleEn = "Think Like a Designer",
            titleHi = "एक डिजाइनर की तरह सोचें",
            subtitleEn = "Level 1.6 • Design Thinking",
            subtitleHi = "लेवल 1.6 • डिजाइन थिंकिंग",
            durationMin = 25,
            readingEn = "Design Thinking is a 5-step problem-solving loop: 1. Empathize (know your user), 2. Define (pinpoint the problem), 3. Ideate (brainstorm), 4. Prototype (sketch drafts), 5. Test (gather feedback).",
            readingHi = "डिजाइन थिंकिंग एक 5-चरणीय प्रक्रिया है: 1. सहानुभूति (Empathize), 2. परिभाषा (Define), 3. विचार (Ideate), 4. प्रोटोटाइप (Prototype), 5. परीक्षण (Test)।",
            codeSnippet = "Empathize -> Define -> Ideate -> Prototype -> Test",
            keyTerms = listOf("Empathize", "Define Problem", "Ideation", "User Testing")
        ),
        CurriculumLesson(
            id = "lvl_1_7",
            titleEn = "Spy Mode: App Inspection",
            titleHi = "जासूस मोड: ऐप निरीक्षण",
            subtitleEn = "Level 1.7 • App Deconstruction",
            subtitleHi = "लेवल 1.7 • ऐप डिकंस्ट्रक्शन",
            durationMin = 25,
            readingEn = "Inspect world-famous apps to learn their tricks. Swiggy uses vibrant contrast buttons for fast clicks, Spotify uses cozy dark themes for late-night sessions, and Instagram keeps navigation at the bottom thumb-reach zone.",
            readingHi = "प्रसिद्ध ऐप्स से यूआई ट्रिक्स सीखें। स्विगी चमकीले बटनों का उपयोग करती है, स्पॉटिफ़ाई आरामदायक डार्क थीम का उपयोग करता है, और इंस्टाग्राम नीचे बटन रखता है।",
            codeSnippet = "Contrast Actions | Dark Themes | Thumb-Zone Navigation",
            keyTerms = listOf("Contrast Action", "Thumb Zone", "Ergonomics")
        ),
        CurriculumLesson(
            id = "lvl_1_8",
            titleEn = "Your First Game (Without Coding!)",
            titleHi = "आपका पहला गेम (बिना कोडिंग के!)",
            subtitleEn = "Level 1.8 • Sequencing & Logic",
            subtitleHi = "लेवल 1.8 • अनुक्रमण और तर्क",
            durationMin = 25,
            readingEn = "Before typing code, master logic sequencing—ordering commands step-by-step. Block-based tools (like Scratch) let you drag puzzle pieces to control sprites, demonstrating how order directly controls results.",
            readingHi = "कोडिंग से पहले निर्देशों को चरण-दर-चरण क्रमित करना सीखें। स्क्रैच (Scratch) जैसे विजुअल ब्लॉक टूल आपको स्प्राइट्स को नियंत्रित करना सिखाते हैं।",
            codeSnippet = "[When Green Flag Clicked]\n  [Move 10 Steps]\n  [Play Sound 'Dance_Beat']",
            keyTerms = listOf("Visual Blocks", "Sprites", "Sequencing")
        ),
        CurriculumLesson(
            id = "lvl_1_9",
            titleEn = "The Loop Secret",
            titleHi = "लूप का रहस्य",
            subtitleEn = "Level 1.9 • Introduction to Loops",
            subtitleHi = "लेवल 1.9 • लूप का परिचय",
            durationMin = 20,
            readingEn = "Instead of writing instructions multiple times, programmers use Loops to repeat actions automatically. A Repeat 10 block runs logic 10 times and stops, keeping code short and powerful.",
            readingHi = "निर्देशों को कई बार लिखने के बजाय प्रोग्रामर स्वचालित रूप से दोहराने के लिए 'लूप्स' (Loops) का उपयोग करते हैं। यह कोड को छोटा और शक्तिशाली बनाता है।",
            codeSnippet = "[Repeat 10]\n  [Next Costume (Animation)]\n  [Wait 0.1s]",
            keyTerms = listOf("Loops", "Repeat Blocks", "Efficiency")
        ),
        CurriculumLesson(
            id = "lvl_1_10",
            titleEn = "WORLD BOSS: App Critique Project",
            titleHi = "वर्ल्ड बॉस: ऐप क्रिटिक प्रोजेक्ट",
            subtitleEn = "Level 1.10 • Portfolio Project",
            subtitleHi = "लेवल 1.10 • पोर्टफोलियो प्रोजेक्ट",
            durationMin = 30,
            readingEn = "Time to defeat the World Boss! Identify three usability flaws on any favorite mobile app (such as bad colors or hidden buttons) and sketch paper wireframes showing how you would redesign it better.",
            readingHi = "वर्ल्ड बॉस को हराने का समय आ गया है! अपने पसंदीदा मोबाइल ऐप में तीन खामियां खोजें और पेपर वायरफ़्रेम पर एक बेहतर डिज़ाइन स्केच करें।",
            codeSnippet = "Critique: Spot Confusing Flow -> Note Contrast Issue -> Sketch Redesign",
            keyTerms = listOf("Usability", "Redesign", "Critique Report"),
            difficulty = "Intermediate"
        )
    )

    private val world2Lessons = listOf(
        CurriculumLesson(
            id = "lvl_2_1",
            titleEn = "Sketch Your Dream App",
            titleHi = "अपने सपनों के ऐप का स्केच बनाएं",
            subtitleEn = "Level 2.1 • Wireframing",
            subtitleHi = "लेवल 2.1 • वायरफ्रेमिंग",
            durationMin = 20,
            readingEn = "A Wireframe is a basic black-and-white sketch showing where buttons, titles, and pictures sit on the screen. It allows designers to quickly test ideas with paper and pencil without coding.",
            readingHi = "एक वायरफ़्रेम स्क्रीन पर बटन, टेक्स्ट और छवियों की स्थिति दिखाने वाला साधारण ब्लैक-एंड-व्हाइट स्केच है। यह विचारों के परीक्षण को बहुत तेज़ बनाता है।",
            codeSnippet = "[ Image: Crossed Box ]\n[ Title: Double Line ]\n[ Button: Border Box ]",
            keyTerms = listOf("Wireframes", "Mockup Boxes", "Paper Prototyping")
        ),
        CurriculumLesson(
            id = "lvl_2_2",
            titleEn = "The Psychology of Colors",
            titleHi = "रंगों का मनोविज्ञान",
            subtitleEn = "Level 2.2 • Color Theory",
            subtitleHi = "लेवल 2.2 • कलर थ्योरी",
            durationMin = 20,
            readingEn = "Colors prompt emotions. Blue builds trust (Facebook, Banks), red sparks urgency and appetite (Zomato, Swiggy). Designers use Hex Codes (e.g. #001255) to declare precise digital shades in code.",
            readingHi = "रंग भावनाएं जगाते हैं। नीला विश्वास जगाता है, लाल तात्कालिकता और भूख को बढ़ाता है। सटीक रंगों के लिए कोडिंग में हेक्स कोड (Hex Codes) का उपयोग होता है।",
            codeSnippet = "Primary: #001255 (Trust Blue)\nAccent: #FF9800 (Energy Orange)",
            keyTerms = listOf("Color Psychology", "Hex Codes", "Contrast Ratio")
        ),
        CurriculumLesson(
            id = "lvl_2_3",
            titleEn = "Fonts That Speak",
            titleHi = "बोलने वाले फॉन्ट्स",
            subtitleEn = "Level 2.3 • Typography Basics",
            subtitleHi = "लेवल 2.3 • टाइपोग्राफी बेसिक्स",
            durationMin = 15,
            readingEn = "Typography is the layout of text. Serif fonts feel serious, while Sans-Serif fonts feel friendly and modern. Limit apps to two font families and establish a strict size hierarchy (sp).",
            readingHi = "टाइपोग्राफी टेक्स्ट की लिखावट की कला है। सेन्स-सेरिफ़ फॉन्ट साफ, दोस्ताना और आधुनिक महसूस होते हैं। अपने ऐप्स में दो से अधिक फॉन्ट न रखें।",
            codeSnippet = "Title: Bold Sans (24sp) | Body: Regular (16sp)",
            keyTerms = listOf("Sans-Serif", "Font Weights", "Scale Hierarchy")
        ),
        CurriculumLesson(
            id = "lvl_2_4",
            titleEn = "The Invisible Grid",
            titleHi = "अदृश्य ग्रिड",
            subtitleEn = "Level 2.4 • Layout Principles",
            subtitleHi = "लेवल 2.4 • लेआउट सिद्धांत",
            durationMin = 20,
            readingEn = "Professional layouts align elements along an invisible grid. We use an 8dp spacing scale (8dp, 16dp, 24dp) to organize content, maintaining balanced padding and margins so screens look clean.",
            readingHi = "पेशेवर लेआउट एक अदृश्य ग्रिड में संरेखित होते हैं। कंटेंट व्यवस्थित करने के लिए हम 8dp स्पेसिंग स्केल (8dp, 16dp, 24dp) का उपयोग करते हैं।",
            codeSnippet = "Margin: 16dp | Card Padding: 12dp | Spacing: 8dp",
            keyTerms = listOf("8dp Spacing", "Grids", "Outer Margins")
        ),
        CurriculumLesson(
            id = "lvl_2_5",
            titleEn = "Click Me! (Button Design)",
            titleHi = "क्लिक मी! (बटन डिजाइन)",
            subtitleEn = "Level 2.5 • UI Elements",
            subtitleHi = "लेवल 2.5 • UI एलिमेंट्स",
            durationMin = 15,
            readingEn = "High-quality button design includes rounded corners, high color contrast for primary actions, and immediate visual states (changes when tapped). Ensure buttons meet the 48dp accessibility touch target.",
            readingHi = "उच्च-गुणवत्ता वाले बटन में गोल कोने, स्पष्ट रंग कंट्रास्ट, और तत्काल विजुअल फीडबैक शामिल होते हैं। बटनों का आकार कम से कम 48dp होना चाहिए।",
            codeSnippet = "Button: CornerRadius(8dp) | TouchTarget(48dp)",
            keyTerms = listOf("Touch Targets", "Button States", "Accessibility")
        ),
        CurriculumLesson(
            id = "lvl_2_6",
            titleEn = "Mobile-First Magic",
            titleHi = "मोबाइल-फर्स्ट मैजिक",
            subtitleEn = "Level 2.6 • Responsive Design",
            subtitleHi = "लेवल 2.6 • रिस्पॉन्सिव डिजाइन",
            durationMin = 20,
            readingEn = "Designing 'Mobile-First' means planning for the smallest screens first. We use fluid layout containers instead of fixed pixel widths, letting elements arrange automatically across wide tablets and laptops.",
            readingHi = "मोबाइल-फर्स्ट का मतलब है पहले छोटी स्क्रीन के लिए लेआउट बनाना। हम फिक्स्ड चौड़ाई के बजाय लचीले विड्थ कंटेनरों का उपयोग करते हैं।",
            codeSnippet = "Width: 100% (MaxWidth: 600dp)",
            keyTerms = listOf("Mobile-First", "Fluid Layouts", "Responsive Columns")
        ),
        CurriculumLesson(
            id = "lvl_2_7",
            titleEn = "Don't Get Lost!",
            titleHi = "खो मत जाना!",
            subtitleEn = "Level 2.7 • User Flow & Navigation",
            subtitleHi = "लेवल 2.7 • यूजर फ्लो और नेविगेशन",
            durationMin = 20,
            readingEn = "Clear navigation tells users where they are and how to go back. We use bottom bars for primary screens, drawer menus for details, and left back-arrows so users never get lost.",
            readingHi = "नेविगेशन यह बताता है कि उपयोगकर्ता कहाँ हैं और पीछे कैसे जाना है। हम मुख्य स्क्रीन के लिए नीचे की बार (Bottom Bar) और बैक बटन का उपयोग करते हैं।",
            codeSnippet = "[Home] -> Click Card -> [Detail Screen (Back Button)]",
            keyTerms = listOf("Bottom Navigation", "Backstack Flow", "Navigation Icons")
        ),
        CurriculumLesson(
            id = "lvl_2_8",
            titleEn = "Design for Real People",
            titleHi = "वास्तविक लोगों के लिए डिजाइन",
            subtitleEn = "Level 2.8 • User Personas",
            subtitleHi = "लेवल 2.8 • यूजर पर्सनास",
            durationMin = 20,
            readingEn = "To build a great app, study fictional profiles of typical users (User Personas). Understanding their needs, budget phones, or eyesight constraints allows you to design intuitive, accessible interfaces.",
            readingHi = "शानदार ऐप बनाने के लिए, अपने असली उपयोगकर्ताओं का विवरण (User Persona) तैयार करें। उनकी आवश्यकताओं को समझने से घर्षण रहित अनुभव मिलता है।",
            codeSnippet = "Persona: Farmer Rajesh (Wants large text, Hindi translation)",
            keyTerms = listOf("Target Audience", "User Persona", "Frictionless UI")
        ),
        CurriculumLesson(
            id = "lvl_2_9",
            titleEn = "From Sketch to Clickable",
            titleHi = "स्केच से क्लिक करने योग्य",
            subtitleEn = "Level 2.9 • Prototyping",
            subtitleHi = "लेवल 2.9 • प्रोटोटाइपिंग",
            durationMin = 25,
            readingEn = "Clickable Prototypes connect static mockup screens using hot-spots. When clicked, it moves to the target screen. This simulates a real app to collect valuable feedback before coding.",
            readingHi = "क्लिक करने योग्य प्रोटोटाइप स्थिर डिज़ाइनों को जोड़ते हैं। यह कोडिंग शुरू करने से पहले ऐप के वास्तविक काम का परीक्षण करने में मदद करता है।",
            codeSnippet = "OnClick('Submit') -> Trigger SlideLeft -> NavigateTo(SuccessScreen)",
            keyTerms = listOf("Hotspots", "Transitions", "Figma Mockup")
        ),
        CurriculumLesson(
            id = "lvl_2_10",
            titleEn = "WORLD BOSS: Design a Food App",
            titleHi = "WORLD BOSS: एक फूड ऐप डिजाइन करें",
            subtitleEn = "Level 2.10 • Portfolio Project",
            subtitleHi = "लेवल 2.10 • पोर्टफोलियो प्रोजेक्ट",
            durationMin = 35,
            readingEn = "To defeat this World Boss and earn the 'Visual Artist' badge, build a wireframe design for a Food Delivery App. Include a menu Resturant List, product selection screen, and cart checkout flow.",
            readingHi = "विजुअल आर्टिस्ट बैज पाने के लिए एक फूड डिलीवरी ऐप की तीन स्क्रीन का वायरफ़्रेम स्केच करें: होम, मेनू, और कार्ट चेकआउट फ्लो।",
            codeSnippet = "Wireframes: Home Restaurant List -> Menu Details -> Cart Summary",
            keyTerms = listOf("Visual Artist", "Bilingual Support", "M3 Design"),
            difficulty = "Intermediate"
        )
    )

    private val world3Lessons = listOf(
        CurriculumLesson(
            id = "lvl_3_1",
            titleEn = "The Skeleton of a Website",
            titleHi = "वेबसाइट का ढांचा",
            subtitleEn = "Level 3.1 • HTML Structure",
            subtitleHi = "लेवल 3.1 • HTML स्ट्रक्चर",
            durationMin = 20,
            readingEn = "HTML is the bone structure of webpages. We use semantic tags like <header>, <main>, <footer>, and headings to define meaning, guiding the browser on how to structure linear documents.",
            readingHi = "HTML वेबपृष्ठों की रीढ़ की हड्डी की तरह है। हम दस्तावेज़ की संरचना को स्पष्ट करने के लिए <header>, <main>, <footer> जैसे अर्थपूर्ण टैग का उपयोग करते हैं।",
            codeSnippet = "<!DOCTYPE html>\n<html>\n  <body><h1>Hello</h1></body>\n</html>",
            keyTerms = listOf("HTML Tags", "Semantic Elements", "Document Tree")
        ),
        CurriculumLesson(
            id = "lvl_3_2",
            titleEn = "Your Name in BIG Letters",
            titleHi = "आपका नाम बड़े अक्षरों में",
            subtitleEn = "Level 3.2 • Text Formatting",
            subtitleHi = "लेवल 3.2 • टेक्स्ट फॉर्मेटिंग",
            durationMin = 15,
            readingEn = "Headings range from <h1> (largest) to <h6> (smallest). Paragraphs are inside <p> tags. Format content with inline tags like <strong> (bold), <em> (italics), or <blockquote> for quotes.",
            readingHi = "हेडिंग <h1> से <h6> तक होती हैं। पैराग्राफ को <p> के अंदर लिखा जाता है। महत्वपूर्ण शब्दों को <strong> या <em> से सजाएं।",
            codeSnippet = "<h1>Dishara Portal</h1>\n<p>Start <strong>coding</strong> now!</p>",
            keyTerms = listOf("Headings", "Paragraph Tags", "Inline Formatting")
        ),
        CurriculumLesson(
            id = "lvl_3_3",
            titleEn = "Add Your Favorite Meme",
            titleHi = "अपना पसंदीदा मीम जोड़ें",
            subtitleEn = "Level 3.3 • Media & Links",
            subtitleHi = "लेवल 3.3 • मल्टीमीडिया और लिंक्स",
            durationMin = 20,
            readingEn = "Add images using <img> with the 'src' attribute. Embed link paths via anchor tags <a> with an 'href'. Create structured data with lists (<ul> for bullets, <ol> for numbers).",
            readingHi = "चित्र जोड़ने के लिए 'src' के साथ <img> टैग और लिंक के लिए 'href' के साथ <a> टैग का उपयोग करें। सूचियों के लिए <ul> या <ol> चुनें।",
            codeSnippet = "<img src=\"meme.jpg\" alt=\"meme\">\n<a href=\"#\">Link</a>",
            keyTerms = listOf("Image Src", "Anchor Href", "Structured Lists")
        ),
        CurriculumLesson(
            id = "lvl_3_4",
            titleEn = "The Makeup Artist (CSS)",
            titleHi = "मेकअप आर्टिस्ट (CSS)",
            subtitleEn = "Level 3.4 • Styling Basics",
            subtitleHi = "लेवल 3.4 • स्टाइलिंग बेसिक्स",
            durationMin = 20,
            readingEn = "CSS styles HTML structures. We write CSS selectors matching tags, then declare style properties like background-color, text-color, fonts, and borders to establish custom color themes.",
            readingHi = "CSS वेबपेज को रंग और साज-सज्जा देता है। हम HTML तत्वों को सजाने के लिए CSS चयनकर्ताओं और नियमों (जैसे पृष्ठभूमि रंग, फ़ॉन्ट) का उपयोग करते हैं।",
            codeSnippet = "h1 {\n  color: #001255;\n  font-family: sans-serif;\n}",
            keyTerms = listOf("Selectors", "Style Rules", "Colors & Fonts")
        ),
        CurriculumLesson(
            id = "lvl_3_5",
            titleEn = "The Box Secret",
            titleHi = "बॉक्स का रहस्य",
            subtitleEn = "Level 3.5 • Box Model",
            subtitleHi = "लेवल 3.5 • बॉक्स मॉडल",
            durationMin = 20,
            readingEn = "Every HTML page element is a rectangle governed by the Box Model. It consists of the core Content area, internal Padding spacing, the visual Border line, and external Margin offsets pushing items apart.",
            readingHi = "वेबपेज पर प्रत्येक तत्व एक बॉक्स है। इसमें कंटेंट, आंतरिक पैडिंग (Padding), बाहरी मार्जिन (Margin) और बॉर्डर (Border) शामिल हैं।",
            codeSnippet = ".card {\n  padding: 16px;\n  border: 1px solid;\n  margin: 10px;\n}",
            keyTerms = listOf("Box Model", "Inner Padding", "Outer Margin")
        ),
        CurriculumLesson(
            id = "lvl_3_6",
            titleEn = "Frog Grid (Flexbox)",
            titleHi = "फ्रॉग ग्रिड (Flexbox)",
            subtitleEn = "Level 3.6 • Flexbox Layouts",
            subtitleHi = "लेवल 3.6 • फ्लेक्सबॉक्स लेआउट",
            durationMin = 25,
            readingEn = "Flexbox aligns items dynamically inside layout rows or columns. By writing 'display: flex;' on containers, we justify content and align elements evenly, preventing broken mobile views.",
            readingHi = "फ्लेक्सबॉक्स तत्वों को व्यवस्थित करने का साधन है। कंटेनर में 'display: flex;' घोषित करने से आइटम स्वचालित रूप से फिट होते हैं।",
            codeSnippet = ".box {\n  display: flex;\n  justify-content: center;\n}",
            keyTerms = listOf("Display Flex", "Justify Content", "Align Items")
        ),
        CurriculumLesson(
            id = "lvl_3_7",
            titleEn = "Your First 3-Page Website",
            titleHi = "आपकी पहली 3-पेज की वेबसाइट",
            subtitleEn = "Level 3.7 • Site Navigation",
            subtitleHi = "लेवल 3.7 • साइट नेविगेशन",
            durationMin = 25,
            readingEn = "Connect index.html, about.html, and contact.html together. Using anchor tags inside a common navigation bar (<nav>), we allow visitors to skip seamlessly across different pages.",
            readingHi = "index.html, about.html और contact.html को आपस में जोड़ें। एंकर टैग (<nav>) का उपयोग करके बहु-पृष्ठ वेबसाइट तैयार करें।",
            codeSnippet = "<nav>\n  <a href=\"index.html\">Home</a>\n  <a href=\"about.html\">About</a>\n</nav>",
            keyTerms = listOf("Multi-page Nav", "Relative Links", "Common Layouts")
        ),
        CurriculumLesson(
            id = "lvl_3_8",
            titleEn = "Make It Interactive!",
            titleHi = "इसे इंटरैक्टिव बनाएं!",
            subtitleEn = "Level 3.8 • JavaScript Intro",
            subtitleHi = "लेवल 3.8 • जावास्क्रिप्ट परिचय",
            durationMin = 20,
            readingEn = "JavaScript adds life to static HTML. We can listen to clicks or key events on any element. When triggered, JS runs function handlers to dynamically edit texts or display alert notifications.",
            readingHi = "जावास्क्रिप्ट वेबपेज को सजीव बनाता है। हम बटन क्लिक को सुन सकते हैं और सामग्री को गतिशील रूप से बदल सकते हैं।",
            codeSnippet = "btn.addEventListener('click', () => {\n  alert('Hello!');\n});",
            keyTerms = listOf("Event Listeners", "Click Events", "DOM Queries")
        ),
        CurriculumLesson(
            id = "lvl_3_9",
            titleEn = "Dark Mode Toggle",
            titleHi = "डार्क मोड टॉगल",
            subtitleEn = "Level 3.9 • Toggle Logic",
            subtitleHi = "लेवल 3.9 • टॉगल लॉजिक",
            durationMin = 25,
            readingEn = "Build a real dark mode toggling mechanism. Using JavaScript conditionals, we check if the body contains a '.dark' theme class, and toggle it on or off dynamically when the user clicks.",
            readingHi = "कंडीशनल्स और क्लास सूचियों (classList) का उपयोग करके एक डार्क मोड स्विच बनाएं जो बटन दबाने पर रंगों को बदले।",
            codeSnippet = "btn.addEventListener('click', () => {\n  document.body.classList.toggle('dark');\n});",
            keyTerms = listOf("Class Toggle", "JS Conditionals", "Interactive Swaps")
        ),
        CurriculumLesson(
            id = "lvl_3_10",
            titleEn = "WORLD BOSS: Digital Business Card",
            titleHi = "WORLD BOSS: डिजिटल बिजनेस कार्ड",
            subtitleEn = "Level 3.10 • Portfolio Project",
            subtitleHi = "लेवल 3.10 • पोर्टफोलियो प्रोजेक्ट",
            durationMin = 30,
            readingEn = "For this capstone World Boss, build a beautiful online 'Digital Business Card'. Include a profile headshot, responsive spacing, social link icons, and a working dark mode toggle.",
            readingHi = "वेब डेवलपर बैज पाने के लिए एक डिजिटल बिजनेस कार्ड पेज बनाएं जिसमें फोटो, बायो, लिंक और काम करने वाला डार्क मोड स्विच हो।",
            codeSnippet = "Checklist: HTML Structure -> CSS Box Spacing -> JS Toggle Switch",
            keyTerms = listOf("Card Portfolio", "Bilingual Web", "Client Scripts"),
            difficulty = "Advanced"
        )
    )

    private val world4Lessons = listOf(
        CurriculumLesson(
            id = "lvl_4_1",
            titleEn = "The Magic Boxes",
            titleHi = "जादुई बक्से",
            subtitleEn = "Level 4.1 • Variables & Types",
            subtitleHi = "लेवल 4.1 • वेरिएबल्स और टाइप्स",
            durationMin = 15,
            readingEn = "Variables are boxes in computer memory storing data. We declare variables using let or const to capture numbers, strings (text in quotes), and boolean flags (true or false).",
            readingHi = "वेरिएबल्स मेमोरी में डेटा स्टोर करने वाले डिब्बे हैं। हम संख्या (numbers), स्ट्रिंग (text) और बूलियन (true/false) को सहेजने के लिए इनका उपयोग करते हैं।",
            codeSnippet = "const name = \"Dishara\";\nlet score = 95;",
            keyTerms = listOf("Variables", "Data Types", "Memory Assignment")
        ),
        CurriculumLesson(
            id = "lvl_4_2",
            titleEn = "Build Your Own Calculator",
            titleHi = "कैलकुलेटर बनाएं",
            subtitleEn = "Level 4.2 • Basic Operators",
            subtitleHi = "लेवल 4.2 • बेसिक ऑपरेटर",
            durationMin = 20,
            readingEn = "Perform calculations using arithmetic operators: + (addition), - (subtraction), * (multiplication), and / (division). Gather user input inside variables to calculate sums.",
            readingHi = "जोड़ (+), घटाव (-), गुणा (*), और भाग (/) ऑपरेटरों का उपयोग करके गणित करें। वेरिएबल में यूजर इनपुट लेकर परिणाम दिखाएं।",
            codeSnippet = "let result = (num1 + num2) * multiplier;",
            keyTerms = listOf("Arithmetic", "Operators", "Inputs")
        ),
        CurriculumLesson(
            id = "lvl_4_3",
            titleEn = "The Decision Maker",
            titleHi = "निर्णय लेने वाला",
            subtitleEn = "Level 4.3 • If-Else Conditionals",
            subtitleHi = "लेवल 4.3 • कंडीशंस (If-Else)",
            durationMin = 20,
            readingEn = "Conditionals guide program paths. An 'if' block checks if a boolean condition is true. If true, the block executes; otherwise, it branches into the 'else' block scenario.",
            readingHi = "कंडीशंस प्रोग्राम के रास्तों को तय करती हैं। यदि 'if' के अंदर की स्थिति सत्य है, तो पहला कोड ब्लॉक चलता है, अन्यथा 'else' वाला भाग चलता है।",
            codeSnippet = "if (age >= 18) {\n  grantAccess();\n} else {\n  rejectAccess();\n}",
            keyTerms = listOf("Conditionals", "Comparison", "If-Else")
        ),
        CurriculumLesson(
            id = "lvl_4_4",
            titleEn = "The Repetition Machine",
            titleHi = "दोहराव मशीन",
            subtitleEn = "Level 4.4 • Loops (For/While)",
            subtitleHi = "लेवल 4.4 • लूप्स (For/While)",
            durationMin = 20,
            readingEn = "Loops automate repetitive executions. Instead of writing code 50 times, a 'for' or 'while' loop counts iterations and runs blocks continuously until condition becomes false.",
            readingHi = "लूप बार-बार होने वाले कामों को स्वचालन देते हैं। 'for' या 'while' लूप कंडीशन गलत होने तक कोड को दोहराते रहते हैं।",
            codeSnippet = "for (let i = 0; i < 5; i++) {\n  print(i);\n}",
            keyTerms = listOf("Loops", "Counters", "While Loops")
        ),
        CurriculumLesson(
            id = "lvl_4_5",
            titleEn = "The Shopping List",
            titleHi = "शॉपिंग लिस्ट",
            subtitleEn = "Level 4.5 • Arrays & Lists",
            subtitleHi = "लेवल 4.5 • एरेज़ और लिस्ट",
            durationMin = 20,
            readingEn = "Arrays store lists of items in a single ordered variable starting at index 0. We append items with '.push()', remove them with '.pop()', and access the size with '.length'.",
            readingHi = "एरेज़ (Arrays) कई चीजों को क्रमबद्ध रूप से 0 इंडेक्स से सहेजते हैं। हम push() और pop() से लिस्ट को अपडेट करते हैं।",
            codeSnippet = "let items = [\"Ink\", \"Pen\"];\nitems.push(\"Paper\");",
            keyTerms = listOf("Arrays", "List Index", "Push Method")
        ),
        CurriculumLesson(
            id = "lvl_4_6",
            titleEn = "Build Your Own Toolkit",
            titleHi = "टूलकिट बनाएं",
            subtitleEn = "Level 4.6 • Functions Intro",
            subtitleHi = "लेवल 4.6 • फंक्शन्स परिचय",
            durationMin = 15,
            readingEn = "Functions are reusable logic packages. We pass 'parameters' (inputs) into functions and receive 'returns' (outputs) back, organizing code and avoiding duplication.",
            readingHi = "फंक्शन्स (Functions) पुन: प्रयोज्य कोड ब्लॉक हैं। हम इनमें पैरामीटर्स (inputs) भेजते हैं और 'return' से परिणाम प्राप्त करते हैं।",
            codeSnippet = "function add(a, b) {\n  return a + b;\n}",
            keyTerms = listOf("Functions", "Parameters", "Return Values")
        ),
        CurriculumLesson(
            id = "lvl_4_7",
            titleEn = "Find the Secret Number",
            titleHi = "गुप्त संख्या खोजें",
            subtitleEn = "Level 4.7 • Guessing Logic",
            subtitleHi = "लेवल 4.7 • गेसिंग गेम",
            durationMin = 25,
            readingEn = "Apply comparisons to code a guess game. Generate a random secret number, capture input guesses, run conditional matches, and return 'too high' or 'too low' warnings.",
            readingHi = "तुलना लॉजिक से एक गेम बनाएं। यादृच्छिक संख्या (random number) बनाएं, यूजर के अनुमान की तुलना करें और हिंट दें।",
            codeSnippet = "let secret = Math.floor(Math.random() * 10);\nif (guess === secret) win();",
            keyTerms = listOf("Game Logic", "Random", "Score Counters")
        ),
        CurriculumLesson(
            id = "lvl_4_8",
            titleEn = "Talk to the World",
            titleHi = "दुनिया से बात करें",
            subtitleEn = "Level 4.8 • Intro to APIs",
            subtitleHi = "लेवल 4.8 • एपीआई परिचय",
            durationMin = 20,
            readingEn = "API fetches data from other servers worldwide. We write 'fetch()' calls to retrieve database files in JSON (JavaScript Object Notation), unpacking records dynamically.",
            readingHi = "API सर्वरों से डेटा मंगाती है। हम इंटरनेट से डेटा प्राप्त करने के लिए fetch() और JSON प्रारूप का उपयोग करते हैं।",
            codeSnippet = "fetch('https://api.com/data')\n  .then(r => r.json());",
            keyTerms = listOf("Fetch API", "HTTP Requests", "JSON")
        ),
        CurriculumLesson(
            id = "lvl_4_9",
            titleEn = "The Pokemon Finder",
            titleHi = "पोकेमॉन खोजक",
            subtitleEn = "Level 4.9 • API App Project",
            subtitleHi = "लेवल 4.9 • एपीआई प्रोजेक्ट",
            durationMin = 25,
            readingEn = "Create a Pokedex app calling PokeAPI. Users enter a Pokemon ID, fetch detailed records and images from the server, and bind them onto HTML card layouts dynamically.",
            readingHi = "एक पोकेमॉन ऐप बनाएं जो PokeAPI से जुड़े। यूजर के सर्च पर रीयल-टाइम डेटा और फोटो लाकर यूआई में दिखाएं।",
            codeSnippet = "let url = 'https://pokeapi.co/api/' + id;\nfetch(url);",
            keyTerms = listOf("PokeAPI", "Data Binding", "Rendering")
        ),
        CurriculumLesson(
            id = "lvl_4_10",
            titleEn = "WORLD BOSS: Text Adventure Game",
            titleHi = "WORLD BOSS: टेक्स्ट एडवेंचर गेम",
            subtitleEn = "Level 4.10 • Portfolio Project",
            subtitleHi = "लेवल 4.10 • पोर्टफोलियो प्रोजेक्ट",
            durationMin = 30,
            readingEn = "Build a story-based interactive adventure. Players enter choice options (like 1 or 2), nested conditionals decide results, and score trackers save player progress.",
            readingHi = "कहानी-आधारित गेम बनाएं। यूजर विकल्प चुनता है, कंडीशन निर्णय लेती है और खिलाड़ी के लाइफ स्कोर को ट्रैक किया जाता है।",
            codeSnippet = "Story Loop -> Take Input -> Branch Scenario -> Update Player HP",
            keyTerms = listOf("Game Loop", "Conditionals", "Score System"),
            difficulty = "Advanced"
        )
    )

    private val world5Lessons = listOf(
        CurriculumLesson(
            id = "lvl_5_1",
            titleEn = "The Memory Vault",
            titleHi = "मेमोरी वॉल्ट",
            subtitleEn = "Level 5.1 • Databases Intro",
            subtitleHi = "लेवल 5.1 • डेटाबेस परिचय",
            durationMin = 25,
            readingEn = "Refresh deletes temporary variables. Databases (Firebase/JSON/SQLite) act as persistent lockers, storing tables and documents safely so states persist across device sessions.",
            readingHi = "रिफ्रेश पर अस्थायी स्टेट गायब होता है। डेटा को स्थायी रखने के लिए डेटाबेस (Database) फाइलों का उपयोग किया जाता है।",
            codeSnippet = "Collection: Profiles | Doc: kunal -> { score: 120 }",
            keyTerms = listOf("Databases", "Persistent Store", "Cloud Tables")
        ),
        CurriculumLesson(
            id = "lvl_5_2",
            titleEn = "Save Your To-Do List",
            titleHi = "टू-डू सूची सहेजें",
            subtitleEn = "Level 5.2 • CRUD Actions",
            subtitleHi = "लेवल 5.2 • CRUD ऑपरेशन्स",
            durationMin = 25,
            readingEn = "CRUD represents the core pillars of any software: Create (insert record), Read (query tables), Update (modify status), and Delete (remove list entries safely).",
            readingHi = "CRUD सॉफ्टवेयर का आधार है: Create (डेटा बनाएं), Read (पढ़ें), Update (बदलाव करें), और Delete (सुरक्षित रूप से मिटाएं)।",
            codeSnippet = "Create: INSERT INTO tasks...\nUpdate: UPDATE tasks...",
            keyTerms = listOf("Create Records", "Read Queries", "State Updates")
        ),
        CurriculumLesson(
            id = "lvl_5_3",
            titleEn = "The Secret Password",
            titleHi = "गुप्त पासवर्ड",
            subtitleEn = "Level 5.3 • Authentication",
            subtitleHi = "लेवल 5.3 • प्रमाणीकरण",
            durationMin = 20,
            readingEn = "Authentication protects private dashboards. We build secure Sign Up/Log In frameworks utilizing user IDs, encrypted passes, and session tokens.",
            readingHi = "प्रमाणीकरण (Auth) यूजर के डेटा को सुरक्षित रखता है। इसके लिए पासवर्ड एन्क्रिप्शन और लॉगिन फ्रेमवर्क जरूरी है।",
            codeSnippet = "Auth Flow: Input Pass -> Encrypt Hash -> Match Token",
            keyTerms = listOf("Login Auth", "Encryption", "Security Tokens")
        ),
        CurriculumLesson(
            id = "lvl_5_4",
            titleEn = "Connect the Dots",
            titleHi = "कड़ी से कड़ी जोड़ें",
            subtitleEn = "Level 5.4 • Integration",
            subtitleHi = "लेवल 5.4 • इंटीग्रेशन",
            durationMin = 25,
            readingEn = "Connect UI layers directly to database servers. Initialize SDK libraries, and set up live snapshot listeners that repaint screens automatically as records update.",
            readingHi = "फ्रंटएंड यूआई को डेटाबेस से जोड़ें। snapshot लिसनर्स की मदद से डेटा बदलते ही यूआई खुद-ब-खुद अपडेट होगा।",
            codeSnippet = "database.listenToChanges(data => updateScreen(data));",
            keyTerms = listOf("Bidirectional Sync", "Snapshot", "API Integration")
        ),
        CurriculumLesson(
            id = "lvl_5_5",
            titleEn = "The Bug Hunter",
            titleHi = "बग हंटर",
            subtitleEn = "Level 5.5 • Debugging",
            subtitleHi = "लेवल 5.5 • डिबगिंग",
            durationMin = 20,
            readingEn = "Debugging solves logic failures. Read console error stacks, insert checkpoints, and handle runtime exceptions cleanly inside try-catch code blocks.",
            readingHi = "डिबगिंग गलतियों को ठीक करने का कौशल है। एरर स्टैक ट्रैक पढ़ना और 'try-catch' ब्लॉकों से गलतियां सुधारना सीखें।",
            codeSnippet = "try {\n  parseData(faulty);\n} catch(err) { logError(err); }",
            keyTerms = listOf("Console Logs", "Try-Catch Blocks", "Runtime Error")
        ),
        CurriculumLesson(
            id = "lvl_5_6",
            titleEn = "Beta Testing",
            titleHi = "बीटा टेस्टिंग",
            subtitleEn = "Level 5.6 • Feedback Loops",
            subtitleHi = "लेवल 5.6 • यूजर फीडबैक",
            durationMin = 20,
            readingEn = "Beta launch your application before public deployment. Gather logs of user friction, identify where buttons feel confusing, and iterate on alignments.",
            readingHi = "सार्वजनिक लॉन्च से पहले बीटा वर्जन जारी करें। यूजर की परेशानियों को समझें और बटन लेआउट में आवश्यक सुधार करें।",
            codeSnippet = "User Friction: Missing back arrow -> Fix: Top bar check",
            keyTerms = listOf("Friction Log", "Beta Release", "Design Tweaks")
        ),
        CurriculumLesson(
            id = "lvl_5_7",
            titleEn = "Deploy to the World!",
            titleHi = "दुनिया में तैनात करें!",
            subtitleEn = "Level 5.7 • Cloud Hosting",
            subtitleHi = "लेवल 5.7 • क्लाउड होस्टिंग",
            durationMin = 25,
            readingEn = "Make pages viewable to anyone. Use cloud hosts (Vercel, Netlify) to host directories, configure URLs, and share active links over the internet.",
            readingHi = "अपनी साइट लाइव करें। नेटलिफाई (Netlify) या वर्सेल जैसी क्लाउड होस्टिंग पर फाइलें चढ़ाएं और अपना डोमेन यूआरएल पाएं।",
            codeSnippet = "Deploy Status: SUCCESS | URL: https://study.netlify.app",
            keyTerms = listOf("Cloud Deploy", "Hosts", "Public Domains")
        ),
        CurriculumLesson(
            id = "lvl_5_8",
            titleEn = "Make It Mobile-Ready",
            titleHi = "मोबाइल-तैयार बनाएं",
            subtitleEn = "Level 5.8 • Progressive Web Apps",
            subtitleHi = "लेवल 5.8 • PWA परिचय",
            durationMin = 20,
            readingEn = "Progressive Web Apps let websites install natively. Configure a manifest.json file to trigger custom icons and standalone splash screen themes.",
            readingHi = "PWA वेबसाइट को मोबाइल ऐप की तरह काम करने की क्षमता देता है। manifest.json में आइकॉन और स्टाइल सेट करें।",
            codeSnippet = "{\n  \"display\": \"standalone\",\n  \"start_url\": \"/index.html\"\n}",
            keyTerms = listOf("PWA Manifest", "Standalone", "Installability")
        ),
        CurriculumLesson(
            id = "lvl_5_9",
            titleEn = "Polish & Shine",
            titleHi = "पॉलिश और शाइन",
            subtitleEn = "Level 5.9 • UI Refinement",
            subtitleHi = "लेवल 5.9 • विजुअल पॉलिश",
            durationMin = 20,
            readingEn = "Refining UI turns simple software into elite products. Apply soft shadow elevations on cards, add button hover transitions, and build loading spinners.",
            readingHi = "यूआई में सुधार करके ऐप सुंदर बनता है। कार्ड्स पर हल्की छाया, होवर इफ़ेक्ट और लोडिंग प्रोग्रेस बार जोड़ें।",
            codeSnippet = ".card {\n  transition: transform 0.2s;\n}",
            keyTerms = listOf("Visual Polish", "Elevations", "Spinners")
        ),
        CurriculumLesson(
            id = "lvl_5_10",
            titleEn = "FINAL BOSS: Study Buddy App",
            titleHi = "FINAL BOSS: स्टडी बडी ऐप",
            subtitleEn = "Level 5.10 • Capstone Project",
            subtitleHi = "लेवल 5.10 • कैपस्टोन प्रोजेक्ट",
            durationMin = 35,
            readingEn = "Become a Master Creator! Build a full-stack 'Study Buddy' planner. Features: secure auth, SQLite task CRUD, interactive countdown timers, and cloud URLs.",
            readingHi = "मास्टर क्रिएटर बनें! एक स्टडी बडी प्लानर बनाएं जिसमें ऑथ, टास्क CRUD, टाइमर और क्लाउड होस्टिंग का तालमेल हो।",
            codeSnippet = "Capstone: Auth SignUp -> Task CRUD SQLite -> Live Vercel Host",
            keyTerms = listOf("Study Planner", "Full Stack", "Live Deploy"),
            difficulty = "Advanced"
        )
    )

    private val dlcLessons = listOf(
        CurriculumLesson(
            id = "dlc_ai",
            titleEn = "AI Copilot & Debugging",
            titleHi = "एआई कोपायलट और डिबगिंग",
            subtitleEn = "DLC 1 • AI Tools",
            subtitleHi = "डीएलसी 1 • एआई टूल्स",
            durationMin = 25,
            readingEn = "AI tools like ChatGPT help explain complex concepts and fix syntax errors. Learn prompt techniques to query AI responsibly without losing analytical thinking.",
            readingHi = "चैटजीपीटी और एआई टूल्स कठिन सिद्धांतों को समझने और कोडिंग समस्याओं को सुलझाने में सहायता करते हैं। नैतिक रूप से पूछना सीखें।",
            codeSnippet = "Prompt: Explain JS fetch exception handling using try-catch blocks.",
            keyTerms = listOf("Prompt Techniques", "Ethical AI", "Debugging Assistance")
        ),
        CurriculumLesson(
            id = "dlc_git",
            titleEn = "Git & GitHub Version Control",
            titleHi = "गिट और गिटहब वर्शन कंट्रोल",
            subtitleEn = "DLC 2 • Teamwork Flow",
            subtitleHi = "डीएलसी 2 • टीम वर्क",
            durationMin = 30,
            readingEn = "Git is a version control system serving as a time machine for code. Learn git init, commits, push commands, branch management, and merging pull requests.",
            readingHi = "गिट (Git) कोड की टाइम मशीन है। गिटहब पर कोड अपलोड करना (push), कमिट करना और शाखाएं (branches) बनाना सीखें।",
            codeSnippet = "git init\ngit add .\ngit commit -m 'Initial commit'",
            keyTerms = listOf("Commit Logs", "Branching", "Pull Requests")
        ),
        CurriculumLesson(
            id = "dlc_react",
            titleEn = "React JS Basics",
            titleHi = "रिएक्ट जेएस",
            subtitleEn = "DLC 3 • Frontend Frameworks",
            subtitleHi = "डीएलसी 3 • वेब फ्रेमवर्क्स",
            durationMin = 30,
            readingEn = "React uses components to build interfaces. We handle layouts dynamically via State hooks, managing reusable segments of cards or inputs easily.",
            readingHi = "रिएक्ट (React) कंपोनेंट्स पर आधारित है। state वेरिएबल की मदद से डायनेमिक और सुंदर यूआई बटन तैयार करें।",
            codeSnippet = "const [count, setCount] = useState(0);\nreturn <button onClick={() => setCount(count + 1)}>{count}</button>;",
            keyTerms = listOf("React Hooks", "Components", "Virtual DOM")
        ),
        CurriculumLesson(
            id = "dlc_python",
            titleEn = "Python Deep Dive & OOP",
            titleHi = "पायथन और OOP",
            subtitleEn = "DLC 4 • Python Language",
            subtitleHi = "डीएलसी 4 • पायथन",
            durationMin = 30,
            readingEn = "Python is beautiful, readable, and highly popular. Master object-oriented concepts like classes, objects, file handling, and simple server scripts.",
            readingHi = "पायथन कोडिंग के लिए एक सरल और लोकप्रिय भाषा है। क्लासेज (Classes), ऑब्जेक्ट्स (Objects) और फाइल हैंडलिंग का ज्ञान पाएं।",
            codeSnippet = "class Student:\n    def __init__(self, name):\n        self.name = name",
            keyTerms = listOf("Python OOP", "Classes", "Script Automation")
        ),
        CurriculumLesson(
            id = "dlc_cyber",
            titleEn = "Cybersecurity & Safe Coding",
            titleHi = "साइबर सुरक्षा",
            subtitleEn = "DLC 5 • Secure Code",
            subtitleHi = "डीएलसी 5 • सिक्योरिटी",
            durationMin = 25,
            readingEn = "Safe coding protects pages. Learn input sanitization to stop SQL injections, secure passwords via hashing, and prevent leaking secrets.",
            readingHi = "सुरक्षित कोडिंग वेबसाइट की रक्षा करती है। SQL इंजेक्शन रोकना, क्रेडेंशियल्स की हिफ़ाज़त करना और पासवर्ड एन्क्रिप्शन सीखें।",
            codeSnippet = "const securePass = bcrypt.hashSync(rawPassword, salt);",
            keyTerms = listOf("SQL Injection", "Password Hashing", "Credential Safety")
        )
    )

    private fun getFastTrackLessons(lessons: List<CurriculumLesson>, modifier: String, durationMultiplier: Double): List<CurriculumLesson> {
        return lessons.map { lesson ->
            lesson.copy(
                id = "${lesson.id}_${modifier.lowercase().replace(" ", "_").replace("-", "_")}",
                subtitleEn = "${lesson.subtitleEn.replace("•", "• $modifier •")}",
                subtitleHi = "${lesson.subtitleHi.replace("•", "• $modifier •")}",
                durationMin = (lesson.durationMin * durationMultiplier).toInt().coerceAtLeast(5)
            )
        }
    }

    val batchesCurriculum = mapOf(
        "udaan" to listOf(
            CurriculumModule(
                id = "world_1_digital_universe",
                titleEn = "World 1: The Digital Universe",
                titleHi = "वर्ल्ड 1: द डिजिटल यूनिवर्स",
                descriptionEn = "Explore computer fundamentals, hardware, internet operations, binary logic, and basic loop-based logic.",
                descriptionHi = "कंप्यूटर के बुनियादी सिद्धांतों, हार्डवेयर, इंटरनेट संचालन, बाइनरी लॉजिक और बेसिक लूप्स को समझें।",
                category = "Coding",
                iconName = "code",
                lessons = world1Lessons
            ),
            CurriculumModule(
                id = "world_2_design_lab",
                titleEn = "World 2: The Design Lab",
                titleHi = "वर्ल्ड 2: द डिजाइन लैब",
                descriptionEn = "Learn wireframing, color psychology, fonts and readability, layout grids, and build interactive digital prototypes.",
                descriptionHi = "वायरफ़्रेमिंग, रंग मनोविज्ञान, फ़ॉन्ट और पठनीयता, लेआउट ग्रिड सीखें, और इंटरैक्टिव डिजिटल प्रोटोटाइप बनाएं।",
                category = "Design",
                iconName = "brush",
                lessons = world2Lessons
            )
        ),
        "batch_10" to listOf(
            CurriculumModule(
                id = "world_1_digital_universe_ft",
                titleEn = "World 1: The Digital Universe (Fast-Track)",
                titleHi = "वर्ल्ड 1: द डिजिटल यूनिवर्स (फास्ट-ट्रैक)",
                descriptionEn = "Fast-track computer fundamentals, hardware, and binary logic in half the time.",
                descriptionHi = "आधे समय में कंप्यूटर के बुनियादी सिद्धांतों, हार्डवेयर और बाइनरी लॉजिक को समझें।",
                category = "Coding",
                iconName = "code",
                lessons = getFastTrackLessons(world1Lessons, "Fast-Track", 0.6)
            ),
            CurriculumModule(
                id = "world_2_design_lab_ft",
                titleEn = "World 2: The Design Lab (Fast-Track)",
                titleHi = "वर्ल्ड 2: द डिजाइन लैब (फास्ट-ट्रैक)",
                descriptionEn = "Learn wireframing, color theory, and responsive layouts at an accelerated pace.",
                descriptionHi = "वायरफ़्रेमिंग, कलर थ्योरी और रिस्पॉन्सिव लेआउट्स को तेज़ गति से सीखें।",
                category = "Design",
                iconName = "brush",
                lessons = getFastTrackLessons(world2Lessons, "Fast-Track", 0.6)
            ),
            CurriculumModule(
                id = "world_3_web_builder",
                titleEn = "World 3: The Web Builder",
                titleHi = "वर्ल्ड 3: द वेब बिल्डर",
                descriptionEn = "Learn HTML5 structural layouts, text formatting tags, CSS selectors, box modeling, and responsive CSS Flexbox grids.",
                descriptionHi = "HTML5 संरचनात्मक लेआउट, टेक्स्ट फ़ॉर्मेटिंग टैग, CSS सिलेक्टर्स, बॉक्स मॉडलिंग और रिस्पॉन्सिव CSS फ्लेक्सबॉक्स ग्रिड सीखें।",
                category = "Coding",
                iconName = "code",
                lessons = world3Lessons
            ),
            CurriculumModule(
                id = "world_4_logician",
                titleEn = "World 4: The Logician",
                titleHi = "वर्ल्ड 4: द लॉजिशियन",
                descriptionEn = "Master variables, loops, arrays, conditionals, functions, and fetch API integrations in coding logic.",
                descriptionHi = "कोडिंग तर्क में वेरिएबल्स, लूप्स, एरेज़, कंडीशनल, फंक्शन्स और फेच एपीआई इंटीग्रेशन में महारत हासिल करें।",
                category = "Coding",
                iconName = "code",
                lessons = world4Lessons
            )
        ),
        "pragati" to listOf(
            CurriculumModule(
                id = "world_1_digital_universe_ft",
                titleEn = "World 1: The Digital Universe (Fast-Track)",
                titleHi = "वर्ल्ड 1: द डिजिटल यूनिवर्स (फास्ट-ट्रैक)",
                descriptionEn = "Fast-track computer fundamentals, hardware, and binary logic in half the time.",
                descriptionHi = "आधे समय में कंप्यूटर के बुनियादी सिद्धांतों, हार्डवेयर और बाइनरी लॉजिक को समझें।",
                category = "Coding",
                iconName = "code",
                lessons = getFastTrackLessons(world1Lessons, "Fast-Track", 0.6)
            ),
            CurriculumModule(
                id = "world_2_design_lab_ft",
                titleEn = "World 2: The Design Lab (Fast-Track)",
                titleHi = "वर्ल्ड 2: द डिजाइन लैब (फास्ट-ट्रैक)",
                descriptionEn = "Learn wireframing, color theory, and responsive layouts at an accelerated pace.",
                descriptionHi = "वायरफ़्रेमिंग, कलर थ्योरी और रिस्पॉन्सिव लेआउट्स को तेज़ गति से सीखें।",
                category = "Design",
                iconName = "brush",
                lessons = getFastTrackLessons(world2Lessons, "Fast-Track", 0.6)
            ),
            CurriculumModule(
                id = "world_3_web_builder_ft",
                titleEn = "World 3: The Web Builder (Fast-Track)",
                titleHi = "वर्ल्ड 3: द वेब बिल्डर (फास्ट-ट्रैक)",
                descriptionEn = "HTML5 structural layouts, CSS styling, and JavaScript events at an accelerated pace.",
                descriptionHi = "HTML5 संरचनात्मक लेआउट, CSS स्टाइलिंग और जावास्क्रिप्ट इवेंट तेज़ गति से सीखें।",
                category = "Coding",
                iconName = "code",
                lessons = getFastTrackLessons(world3Lessons, "Fast-Track", 0.6)
            ),
            CurriculumModule(
                id = "world_4_logician",
                titleEn = "World 4: The Logician",
                titleHi = "वर्ल्ड 4: द लॉजिशियन",
                descriptionEn = "Master variables, loops, arrays, conditionals, functions, and fetch API integrations in coding logic.",
                descriptionHi = "कोडिंग तर्क में वेरिएबल्स, लूप्स, एरेज़, कंडीशनल, फंक्शन्स और फेच एपीआई इंटीग्रेशन में महारत हासिल करें।",
                category = "Coding",
                iconName = "code",
                lessons = world4Lessons
            ),
            CurriculumModule(
                id = "world_5_maker",
                titleEn = "World 5: The Maker",
                titleHi = "वर्ल्ड 5: द मेकर",
                descriptionEn = "Build full-stack database integrations, secure auth, fetch remote APIs, and deploy cloud applications.",
                descriptionHi = "फुल-स्टैक डेटाबेस इंटीग्रेशन, सुरक्षित प्रमाणीकरण, रिमोट एपीआई लाएं और क्लाउड एप्लिकेशन तैनात करें।",
                category = "Coding",
                iconName = "code",
                lessons = world5Lessons
            )
        ),
        "batch_12" to listOf(
            CurriculumModule(
                id = "world_1_digital_universe_vft",
                titleEn = "World 1: The Digital Universe (Very Fast-Track)",
                titleHi = "वर्ल्ड 1: द डिजिटल यूनिवर्स (वेरी फास्ट-ट्रैक)",
                descriptionEn = "Rapid foundation in computer fundamentals, hardware, and binary logic.",
                descriptionHi = "कंप्यूटर के बुनियादी सिद्धांतों, हार्डवेयर और बाइनरी लॉजिक में तीव्र बुनियादी ज्ञान।",
                category = "Coding",
                iconName = "code",
                lessons = getFastTrackLessons(world1Lessons, "Very Fast-Track", 0.4)
            ),
            CurriculumModule(
                id = "world_2_design_lab_vft",
                titleEn = "World 2: The Design Lab (Very Fast-Track)",
                titleHi = "वर्ल्ड 2: द डिजाइन लैब (वेरी फास्ट-ट्रैक)",
                descriptionEn = "Rapid foundation in wireframing, color theory, and layouts.",
                descriptionHi = "वायरफ़्रेमिंग, कलर थ्योरी और लेआउट्स में तीव्र बुनियादी ज्ञान।",
                category = "Design",
                iconName = "brush",
                lessons = getFastTrackLessons(world2Lessons, "Very Fast-Track", 0.4)
            ),
            CurriculumModule(
                id = "world_3_web_builder_vft",
                titleEn = "World 3: The Web Builder (Very Fast-Track)",
                titleHi = "वर्ल्ड 3: द वेब बिल्डर (वेरी फास्ट-ट्रैक)",
                descriptionEn = "Rapid HTML5 structural layouts, CSS styling, and JavaScript interactive actions.",
                descriptionHi = "HTML5 संरचनात्मक लेआउट, CSS स्टाइलिंग और जावास्क्रिप्ट इंटरैक्टिव एक्शन्स का तीव्र ज्ञान।",
                category = "Coding",
                iconName = "code",
                lessons = getFastTrackLessons(world3Lessons, "Very Fast-Track", 0.4)
            ),
            CurriculumModule(
                id = "world_4_logician",
                titleEn = "World 4: The Logician",
                titleHi = "वर्ल्ड 4: द लॉजिशियन",
                descriptionEn = "Master variables, loops, arrays, conditionals, functions, and fetch API integrations in coding logic.",
                descriptionHi = "कोडिंग तर्क में वेरिएबल्स, लूप्स, एरेज़, कंडीशनल, फंक्शन्स और फेच एपीआई इंटीग्रेशन में महारत हासिल करें।",
                category = "Coding",
                iconName = "code",
                lessons = world4Lessons
            ),
            CurriculumModule(
                id = "world_5_maker",
                titleEn = "World 5: The Maker",
                titleHi = "वर्ल्ड 5: द मेकर",
                descriptionEn = "Build full-stack database integrations, secure auth, fetch remote APIs, and deploy cloud applications.",
                descriptionHi = "फुल-स्टैक डेटाबेस इंटीग्रेशन, सुरक्षित प्रमाणीकरण, रिमोट एपीआई लाएं और क्लाउड एप्लिकेशन तैनात करें।",
                category = "Coding",
                iconName = "code",
                lessons = world5Lessons
            ),
            CurriculumModule(
                id = "advanced_dlcs",
                titleEn = "Advanced DLC Modules",
                titleHi = "उन्नत डीएलसी मॉड्यूल",
                descriptionEn = "Explore AI Copilot, Git/GitHub version control, React frameworks, Python OOP, and cybersecurity fundamentals.",
                descriptionHi = "एआई कोपायलट, गिट/गिटहब संस्करण नियंत्रण, रिएक्ट फ्रेमवर्क, पायथन ओओपी और साइबर सुरक्षा बुनियादी सिद्धांतों का अन्वेषण करें।",
                category = "Career",
                iconName = "work",
                lessons = dlcLessons
            )
        ),
        "lakshya" to listOf(
            CurriculumModule(
                id = "world_5_maker",
                titleEn = "World 5: The Maker",
                titleHi = "वर्ल्ड 5: द मेकर",
                descriptionEn = "Build full-stack database integrations, secure credentials, fetch remote APIs, and deploy cloud applications.",
                descriptionHi = "फुल-स्टैक डेटाबेस इंटीग्रेशन बनाएं, क्रेडेंशियल्स सुरक्षित करें, रिमोट एपीआई लाएं, और क्लाउड एप्लिकेशन तैनात करें।",
                category = "Coding",
                iconName = "code",
                lessons = world5Lessons
            ),
            CurriculumModule(
                id = "advanced_dlcs",
                titleEn = "Advanced DLC Modules",
                titleHi = "उन्नत डीएलसी मॉड्यूल",
                descriptionEn = "Explore Git version control, React frameworks, professional portfolio design, and cybersecurity fundamentals.",
                descriptionHi = "गिट संस्करण नियंत्रण, रिएक्ट फ्रेमवर्क, पेशेवर पोर्टफोलियो डिजाइन और साइबर सुरक्षा बुनियादी सिद्धांतों का अन्वेषण करें।",
                category = "Career",
                iconName = "work",
                lessons = dlcLessons
            )
        )
    )

    fun getModulesForBatch(batchId: String): List<CurriculumModule> {
        return batchesCurriculum[batchId] ?: emptyList()
    }

    fun getAllModules(): List<CurriculumModule> {
        return batchesCurriculum.values.flatten()
    }

    fun getLessonById(lessonId: String): CurriculumLesson? {
        return getAllModules().flatMap { it.lessons }.find { it.id == lessonId }
    }

    fun getModuleById(moduleId: String): CurriculumModule? {
        return getAllModules().find { it.id == moduleId }
    }
}
