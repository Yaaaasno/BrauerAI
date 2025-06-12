package com.example.myapplication.ui.theme

import androidx.compose.foundation.text.BasicText

data class BeerInfo(
    val brand: String,
    val detail1: String,
    val detail2: String
)

// beerInfoMap ist keine Member-Variable von BeerInfo, sondern eine eigenständige Map
val beerInfoMap = mapOf(
    "Wolters" to BeerInfo(
        brand = "Hofbrauhaus Wolters GmbH",
        detail1 = "Die Hofbrauhaus Wolters GmbH aus Braunschweig ist eine der ältesten Brauereien Deutschlands und steht für handwerkliche Bierkultur. Bekannt ist sie vor allem für ihr Wolters Pilsener, ein preisgekröntes Bier nach dem Deutschen Reinheitsgebot.",
        detail2 = "Die Brauerei blickt auf eine bewegte Geschichte zurück – von der Gründung im Dreißigjährigen Krieg über die Zerstörung im Zweiten Weltkrieg bis zur Rückkehr zur Unabhängigkeit im Jahr 2006. Heute verbindet sie Tradition mit Moderne, setzt auf Nachhaltigkeit und engagiert sich regional, etwa als Sponsor von Eintracht Braunschweig. Aktuell läuft die Kronkorken-Aktion (Juni–August 2025), und im Wolters Applausgarten finden im Sommer Live-Events statt."
    ),
    "5,0" to BeerInfo(
        brand = "5:0 Original GmbH & Co. KG",
        detail1 = "Die 5:0 Original GmbH & Co. KG aus Berlin ist eine moderne Craft-Brauerei mit Fußball-DNA, gegründet von ehemaligen Bundesliga-Profis. Bekannt ist sie vor allem für ihr erfrischendes 5:0 Lagerbier – ein leicht hopfiges, helles Vollbier, das perfekt zu jedem Spiel oder Fan-Moment passt.",
        detail2 = "Gestartet als Idee zweier Leidenschaftsbierbrauer und Fußballfans, hat sich 5:0 Original schnell zur Ikone in der Berliner Bierszene entwickelt. Die Brauerei verbindet Leidenschaft für handwerkliches Bier mit dem Spirit des Fußballs und engagiert sich aktiv in der Region, unter anderem durch Kooperationen mit Berliner Vereinen und sozialen Projekten. Aktuell unterstützt 5:0 Original den Amateurfußball über die Initiative '5:0 für den Amateurfußball' und ist regelmäßig auf Festivals und Bierveranstaltungen in Berlin vertreten."
    ),
    "Astra Rakete" to BeerInfo(
        brand = "Astra Rakete",
        detail1 = "Astra Rakete ist ein erfrischendes Leichtbier der Hamburger Astra Brauerei und spricht vor allem jüngere Genießer:innen an, die trotzdem auf Geschmack und Qualität nicht verzichten möchten. Mit nur 2,9 % Alkohol und einem besonders milden Aroma ist es ideal für alle, die auch bei warmen Temperaturen oder langen Abenden leicht und locker trinken möchten.",
        detail2 = "Entstanden als moderne Antwort auf den Trend zu leichten, aber geschmackvollen Bieren, hat sich Astra Rakete schnell in der Party- und Festival-Szene etabliert. Die Marke setzt auf jugendliche Designs und aktuelle Themen wie Sommerfeeling, Partyspaß und Erfrischung – mit coolen Kampagnen und Social-Media-Aktivitäten. Aktuell begeistert Astra Rakete unter anderem durch limitierte Sondereditionen und Saisondesigns auf Dosen."
    ),
    "Astra Rotlich" to BeerInfo(
        brand = "Astra Rotlicht",
        detail1 = "Astra Rotlicht ist eine besondere Variante aus dem Hause Astra – ein kräftiges Exportbier mit 5,5 % Alkohol, das durch seine dunkle Farbe und malzige Note besticht. Es ist ideal für alle, die gerne intensiver trinken und dennoch Wert auf die typische Astra-Qualität legen.",
        detail2 = "Astra Rotlicht ist als Gegenstück zum klassischen Astra Pils entwickelt worden und spricht Liebhaber:innen von dunkleren, wärmeren Bierstilen an. Ob in Kneipen, bei privaten Feiern oder im Winterprogramm – Astra Rotlicht steht für Vielfalt im Glas. Aktuell wird die Marke verstärkt in der kalten Jahreszeit beworben, etwa mit saisonalen Werbekampagnen und Kooperationen mit Hamburger Traditionslokalen."
    ),
    "Krombacher" to BeerInfo(
        brand = "Krombacher Brauerei",
        detail1 = "Die Krombacher Brauerei aus Siegen ist eine der größten Privatbrauereien Deutschlands und steht für Qualität sowie Nachhaltigkeit – besonders bekannt ist das Krombacher Pils, das seit vielen Jahren als meistverkauftes Bier Deutschlands gilt.",
        detail2 = "Seit ihrer Gründung im Jahr 1848 blickt die Brauerei auf eine stolze Tradition zurück und verbindet diese mit modernsten Brauverfahren und Umweltengagement. Krombacher setzt auf regionale Wertschöpfungsketten, energieeffiziente Produktion und soziale Verantwortung. Aktuell läuft unter anderem die 'Krombacher Waldpatenschaft' in Zusammenarbeit mit dem Deutschen Roten Kreuz sowie diverse Sport- und Kultur-Sponsoring-Projekte – vom Profifußball bis hin zu regionalen Events."
    ),
    "Veltins" to BeerInfo(
        brand = "Veltins GmbH & Co. KG",
        detail1 = "Die Veltins GmbH & Co. KG aus Meschede-Grevenstein ist eine traditionsreiche Familienbrauerei, die für hochwertige Biere wie das Veltins Pilsener steht – ein nach dem Deutschen Reinheitsgebot gebrautes Vollbier mit klarer, harmonischer Geschmacksstruktur.",
        detail2 = "Seit 1891 steht Veltins für handwerkliche Braukunst kombiniert mit modernster Technik und Nachhaltigkeit. Die Brauerei ist bekannt für ihr Engagement im Profisport, unter anderem als Namenssponsor des Veltins-Arena in Gelsenkirchen, und setzt auf regionale Verbundenheit sowie internationale Präsenz. Aktuell unterstützt Veltins diverse Sport- und Kulturveranstaltungen und betreibt aktiv Klimaschutzprojekte, darunter die Reduzierung von CO₂-Emissionen entlang der Lieferkette."
    ),
    "Kölsch" to BeerInfo(
        brand = "Brülos Brauerei Früh am Dom GmbH & Co. KG",
        detail1 = "Die Kölner Brauerei Früh am Dom ist eine traditionsreiche Hausbrauerei, die seit 1904 kölsche Lebensfreude und Braukunst verkörpert. Bekannt ist sie vor allem für ihr Früh Kölsch – ein mildes, frisches und typisch rheinisches Obergarbier, das in der ganzen Region und darüber hinaus geschätzt wird.",
        detail2 = "Gegründet als klassische Kölner Gasthausbrauerei, hat sich Früh über die Jahrzehnte zum Aushängeschild der Kölner Bierszene entwickelt. Neben der Brautradition steht das Wirtshaus-Erlebnis im Vordergrund – besonders im historischen Brauhaus am Kölner Dom. Die Brauerei engagiert sich stark regional, unter anderem durch Unterstützung lokaler Feste und Vereine. Aktuell läuft unter anderem die Sommersaison mit Wechselbieren sowie das traditionelle Brauhausbier live aus dem Holzfass."
    ),
    "Guinness" to BeerInfo(
        brand = "Guinness Ireland / Diageo Deutschland GmbH",
        detail1 = "Guinness ist das weltberühmte dunkle Stout aus Irland, das seit 1759 in der Dubliner St. James’s Gate Brauerei gebraut wird. Bekannt für seinen intensiven, malzigen Geschmack mit Noten von Kaffee und Schokolade, ist Guinness heute ein globales Biericon – auch in Deutschland beliebt für sein unverwechselbares Brauerbe und die cremige Textur.",
        detail2 = "Hinter Guinness steht eine über 260 Jahre alte Tradition irischer Braukunst. Das Unternehmen ist eng verbunden mit irischer Identität, Kultur und sportlichem Stolz – besonders durch Rugby und GAA (Gaelic Athletic Association). Weltweit engagiert sich Guinness in Nachhaltigkeitsprojekten, wie etwa dem „Water of Life“-Programm zur Verbesserung des Zugangs zu sauberem Trinkwasser. Aktuell läuft unter anderem die Partnerschaft mit lokalen Brauereien und Gastronomiebetrieben, um das Erlebnis 'Perfect Pint' vor Ort zu stärken."
    ),
    "Paulaner" to BeerInfo(
        brand = "Paulaner Brauerei GmbH",
        detail1 = "Die Paulaner Brauerei aus München ist eine der traditionsreichsten Münchner Brauereien und steht für authentische bayerische Bierkultur. Bekannt ist sie vor allem für ihr Paulaner Hefe-Weißbier, ein naturtrübes Weißbier mit fruchtigem Aroma, sowie das Salvator – das Urgestein der Starkbiersorten und Namensgeber des Stils 'Doppelbock'.",
        detail2 = "Seit ihrer Gründung im Jahr 1634 als Klosterbrauerei der Franziskaner (Paulaner) hat die Brauerei eine stolze Geschichte durch Höhen und Tiefen geschrieben – vom Wiederaufbau nach dem Krieg bis zur heutigen Position als Marktführer in Bayern. Paulaner engagiert sich stark regional, etwa durch Sponsoring von FC Bayern München, der Münchner Sicherheitswacht und traditionellen Festen wie dem Oktoberfest. Aktuell läuft unter anderem die Sommerkampagne mit saisonalen Spezialitäten und limitierten Doseneditionen."
    ),
    "Jever" to BeerInfo(
        brand = "Friedrich-Ebert-Brauerei GmbH & Co. KG",
        detail1 = "Die Jever Brauerei aus Nordenham ist eine der bekanntesten Marken in Deutschland und steht für frischen, modernen Norddeutschen Pilsner-Stil. Bekannt ist vor allem das Jever Pilsener – ein herbwürziges Bier mit hohem Wiedererkennungswert, das seit Jahrzehnten Millionen von Bierliebhaber:innen begeistert.",
        detail2 = "Gegründet 1848 hat sich Jever von einer kleinen Küstenbrauerei zur Ikone der deutschen Bierszene entwickelt. Die Marke ist bekannt für ihre humorvolle Werbung, ihren unverwechselbaren 'Jever-Mann' und ihr starkes Engagement in Sport, Kultur und Gesellschaft. Aktuell setzt Jever verstärkt auf Nachhaltigkeit entlang der Lieferkette, unterstützt regionale Projekte und ist präsent bei Großveranstaltungen wie dem Hamburger DOM oder diversen Sommerfestivals."
    ),
    "Flensburger" to BeerInfo(
        brand = "Flensburger Brauerei GmbH",
        detail1 = "Die Flensburger Brauerei ist eine traditionsreiche Norddeutsche Privatbrauerei und steht für klare, erfrischende Biere wie das FLENS Pils – ein charakterstarkes Norddeutsches Pilsner mit kräftigem Hopfenaroma und langem Abgang.",
        detail2 = "Seit ihrer Gründung im Jahr 1888 hat sich die Flensburger Brauerei als feste Größe in der deutschen Bierszene etabliert. Die Brauerei verbindet maritime Lebensfreude mit handwerklicher Qualität und setzt auf regionale Verbundenheit sowie moderne Brautechnologie. Aktuell engagiert sich FLENS verstärkt in Nachhaltigkeitsthemen, unterstützt lokale Kultur- und Sportprojekte und begeistert mit Sommerkampagnen, Festivalpräsenz und limitierten Sondereditionen."
    ),
    "Hasseröder" to BeerInfo(
        brand = "Hasseröder Brauerei GmbH & Co. KG",
        detail1 = "Die Hasseröder Brauerei aus dem Harz ist eine der traditionsreichsten Brauereien Deutschlands und steht für klare, frische Biere wie das Hasseröder Premium Pils – ein nach dem Deutschen Reinheitsgebot gebrautes Pilsener mit feinherbem Geschmack und hoher Trinkfreude.",
        detail2 = "Seit ihrer Gründung im Jahr 1498 blickt die Hasseröder Brauerei auf eine stolze Geschichte zurück und verbindet regionale Tradition mit moderner Braukunst. Die Brauerei ist bekannt für ihre klare Verbundenheit zur Heimatregion Harz und setzt zunehmend auf Nachhaltigkeit, etwa durch kurze Lieferketten und energiesparende Produktion. Aktuell engagiert sich Hasseröder in regionalen Sport- und Kulturprojekten und begeistert mit saisonalen Aktionen sowie limitierten Sondereditionen – besonders in der Sommer- und Festivalzeit."
    ),
    "Kindl" to BeerInfo(
        brand = "Berliner Kindl Brauerei GmbH",
        detail1 = "Die Berliner Kindl Brauerei ist eine traditionsreiche Berliner Privatbrauerei und steht für typisch regionale Biere wie das Berliner Kindl Pilsner – ein frisches, leicht hopfiges Pils mit hohem Trinkfluss, das eng mit der Hauptstadt verbunden ist.",
        detail2 = "Seit ihrer Gründung im 19. Jahrhundert hat die Brauerei die Bierszene Berlins entscheidend geprägt. Heute ist Berliner Kindl ein Synonym für urbane Lebensfreude, sportliches Engagement und regionale Verbundenheit – unter anderem als langjähriger Partner des 1. FC Union Berlin. Die Brauerei setzt verstärkt auf Nachhaltigkeit und lokale Partnerschaften. Aktuell läuft beispielsweise die Sommersaison mit limitierten Sondereditionen und Aktionen rund um das Berliner Bier-Erlebnis."
    ),
    "Augustiner" to BeerInfo(
        brand = "Augustiner-Bräu München GmbH",
        detail1 = "Die Augustiner-Bräu aus München ist die älteste noch existierende und inhabergeführte Münchner Großbrauerei und steht für traditionelles Brauhandwerk nach dem Deutschen Reinheitsgebot. Bekannt ist sie vor allem für ihr Augustiner Hell – ein goldgelbes, mildwürziges Pilsner mit hohem Wiedererkennungswert, das eng mit der bayerischen Bierkultur verbunden ist.",
        detail2 = "Seit ihrer Gründung im Jahr 1328 hat die Brauerei eine stolze Tradition als Teil des ehemaligen Augustinermönchsklosters und ist bis heute in Familienbesitz. Die Brauerei verbindet historisches Erbe mit moderner Braukunst und setzt auf Nachhaltigkeit, regionale Verbundenheit und handwerkliche Qualität. Aktuell betreibt Augustiner verstärkt Klimaschutzinitiativen, ist präsent bei traditionellen Volksfesten wie dem Oktoberfest und bietet Führungen durch die historische Klosterbrauerei an."
    ),
    "Becks Lemon" to BeerInfo(
        brand = "Beck’s Lemon",
        detail1 = "Beck’s Lemon ist eine erfrischende Citrus-Variante des weltbekannten Beck’s Pilsners, das seit über 150 Jahren aus Bremen kommt. Mit einer harmonischen Kombination aus mildem Biergeschmack und spritziger Zitronennote bietet es eine moderne, leicht zugängliche Alternative für alle, die einen frischen Twist zum klassischen Pilsner suchen.",
        detail2 = "Entstanden als Antwort auf wachsende Nachfrage nach fruchtigen Biervarianten, hat sich Beck’s Lemon zu einer festen Größe in der Sommer- und Partygetränke-Szene entwickelt. Die Marke verbindet internationale Präsenz mit deutscher Brautradition und setzt auf Lifestyle, Moderne und Erfrischung. Aktuell steht Beck’s Lemon besonders in der warmen Jahreszeit im Fokus – mit Saisonkampagnen, coolen Designs und Partnern wie dem FC Bayern München oder internationalen Festivals."
    ),
    "Bitburger" to BeerInfo(
        brand = "Bitburger Brauerei GmbH & Co. KG",
        detail1 = "Die Bitburger Brauerei aus der Eifel ist eine der führenden Privatbrauereien Deutschlands und steht für frisches, modernes Pilsner nach dem Deutschen Reinheitsgebot. Bekannt ist vor allem das Bitburger Pils – ein mild-würziges, goldgelbes Pilsner mit hoher Trinkfreude, das zu den meistverkauften Biere Deutschlands zählt.",
        detail2 = "Seit ihrer Gründung im Jahr 1817 verbindet die Bitburger Brauerei Tradition mit Innovation und setzt auf Nachhaltigkeit, regionale Verbundenheit und sportliches Engagement. Die Marke ist eng mit dem deutschen Profisport verbunden – unter anderem als langjähriger Partner der Bundesliga. Aktuell läuft beispielsweise die Sommerkampagne mit Saisonspecials, sowie Projekte rund um Klimaschutz, kurze Lieferketten und lokale Partnerschaften."
    ),
    "Corona" to BeerInfo(
        brand = "Corona Extra",
        detail1 = "Corona Extra ist das ikonenhafte Bier aus Mexiko – ein hellgoldenes, mildes Lagerbier mit klarem, frischem Geschmack und leicht malziger Note. Weltweit bekannt für seine ikonische Kombination aus Zitronenscheibe und sommerlichem Lifestyle, ist Corona das meistverkaufte mexikanische Bier außerhalb Lateinamerikas.",
        detail2 = "Seit seiner Einführung in den 1920er Jahren hat sich Corona zu einer globalen Lifestyle-Marke entwickelt, die für Leichtigkeit, Sommerstimmung und Genuss steht. Hergestellt von der Grupo Modelo (seit 2013 Teil der AB InBev-Gruppe), verbindet Corona traditionelles Brauhandwerk mit internationaler Ausstrahlung. Aktuell ist Corona besonders bei jungen, trendbewussten Konsument:innen beliebt und präsent bei Festivals, Strandpartys und sommerlichen Events."
    ),
    "Desperados" to BeerInfo(
        brand = "Desperados",
        detail1 = "Desperados ist ein ikonischer alkoholischer Limonadenmix aus dem Hause Pernod Ricard und verbindet den Geschmack von Bier mit einer spritzigen, fruchtigen Note – meist in den Varianten Tequila Lime, Mojito oder Pineapple. Bekannt für sein wildes Image, steht Desperados für Partystimmung, Abenteuerlust und jugendlichen Lebensstil.",
        detail2 = "Seit seiner Einführung in den 1990er Jahren hat sich Desperados als Lifestyle-Marke etabliert, die vor allem bei jungen Erwachsenen beliebt ist. Die Marke lebt durch starke Visuals, mutige Werbung und enge Verbindungen zur Festival- und Clubszene. Aktuell ist Desperados verstärkt bei Sommerfestivals, Partys und in der Gastronomie vertreten – mit Schwerpunkt auf trendigen Locations und Eventkooperationen."
    ),
    "Schöfferhofer Grapefruit" to BeerInfo(
        brand = "Schöfferhofer Grapefruit",
        detail1 = "Schöfferhofer Grapefruit ist ein erfrischendes Weizen-Bier-Mischgetränk aus der traditionsreichen Berliner Braunstein Brauerei (heute Teil der Carlsberg Gruppe) und überzeugt durch die harmonische Kombination von mildem Weizenbier und spritziger Grapefruit. Mit 2,5 % Alkohol ist es ideal für alle, die einen leichten, fruchtigen Genuss suchen – besonders in der warmen Jahreszeit.",
        detail2 = "Entstanden als moderne Antwort auf den Trend zu leicht-fruchtigen Biervarianten, hat sich Schöfferhofer Grapefruit schnell als Dauerbrenner in der Sommersaison etabliert. Die Marke verbindet urbane Lebensfreude mit traditionellem Brauhandwerk und setzt auf Lifestyle, Erfrischung und jugendliche Zielgruppen. Aktuell steht Schöfferhofer Grapefruit verstärkt bei Outdoor-Events, Festivals und Sommerpartys im Mittelpunkt – mit klarem Design, klarer Message und hoher Regelmäßigkeit in den Supermarktregalen."
    ),
    "V+" to BeerInfo(
        brand = "V+ (Brauerei C. & A. Veltins GmbH & Co. KG)",
        detail1 = "V+ ist eine moderne Biermischgetränke-Marke der Brauerei Veltins und richtet sich besonders an junge Erwachsene. Die verschiedenen Sorten – etwa V+ Curuba, V+ Lemon oder V+ Cola – verbinden klassisches Bier mit fruchtigen oder koffeinhaltigen Aromen und enthalten rund 2,5–3 % Alkohol.",
        detail2 = "Seit der Einführung 2001 hat sich V+ als trendige Alternative zum klassischen Bier etabliert – mit Fokus auf Party, Eventgastronomie und jüngere Zielgruppen. Die Marke setzt auf moderne Designs, saisonale Aktionen und Social-Media-Kampagnen. Aktuell ist V+ auf zahlreichen Sommer-Events präsent und bietet gelegentlich auch alkoholfreie Varianten unter dem 0,0%-Label an."
    ),
    "Ayinger Dunkel" to BeerInfo(
        brand = "Brauerei Aying",
        detail1 = "Die Brauerei Aying aus dem Münchner Land steht für traditionelles bayerisches Brauhandwerk und ist bekannt für ihr Aying Alt-Bayerisch Dunkel – ein vollmundiges, malzig-aromatisches Dunkles mit samtig-weicher Note und angenehmer Süße. Das Bier wird nach dem Deutschen Reinheitsgebot gebraut und ist ein typischer Vertreter der klassischen Bayerischen Dunkelbiere.",
        detail2 = "Seit ihrer Gründung im Jahr 1905 hat die Familienbrauerei Aying eine stolze Tradition als regional verwurzelte Privatbrauerei. Mit einem klaren Fokus auf handwerkliche Qualität und regionale Verbundenheit setzt Aying auf Authentizität statt Massenmarkt. Aktuell engagiert sich die Brauerei verstärkt in der Förderung von lokalen Kultur- und Brauchtumsveranstaltungen sowie Nachhaltigkeit entlang der Wertschöpfungskette. Das Alt-Bayerisch Dunkel bleibt dabei ein fester Bestandteil sowohl in der Winterkampagne als auch in der Gastronomie mit traditioneller Küche."
    ),
    "Doppelbock" to BeerInfo(
        brand = "Klosterbrauerei Andechs",
        detail1 = "Die Klosterbrauerei Andechs aus dem Benediktinerkloster auf dem Heiligen Berg bei München steht für jahrhundertealte Brautradition und handwerkliche Qualität. Bekannt ist vor allem das Andechser Doppelbock Dunkel – ein vollmundiges, malzreiches Bier mit samtig-weicher Karamellnote, röstigem Aroma und feiner Hopfenbittere, das weltweit Anerkennung als Spitzenvertreter seiner Gattung genießt.",
        detail2 = "Seit über 400 Jahren verbindet die Klosterbrauerei Andechs klösterliches Handwerk mit modernstem Brauwesen und Nachhaltigkeit. Die Biere werden nach dem Deutschen Reinheitsgebot gebraut und spiegeln den Geist der bayerischen Benediktiner wider. Aktuell engagiert sich die Brauerei verstärkt in Umweltprojekten (EMAS-zertifiziert), fördert regionale Kultur und Traditionen und bietet Führungen sowie Bierverkostungen an. Das Doppelbock Dunkel bleibt ein Highlight besonders in der kalten Jahreszeit und wird gern zu festlichen Anlässen gereicht."
    ),
    "Füchschen Altbier" to BeerInfo(
        brand = "Brauerei Füchschen",
        detail1 = "Die Brauerei Füchschen aus Düsseldorf ist eine traditionsreiche, inhabergeführte Privatbrauerei und bekannt für ihr Füchschen Alt – ein klassisches Düsseldorfer Altbier mit harmonischem Bitter-Süß-Spiel, kräftigem Hopfenaroma und langem Abgang. Das Bier wird nach dem Deutschen Reinheitsgebot gebraut und zählt zu den Standardwerken der rheinischen Bierszene.",
        detail2 = "Seit ihrer Gründung im Jahr 1904 hat die Brauerei Füchschen die Tradition des Düsseldorfer Alts geprägt und ist bis heute ein Synonym für handwerkliches Brauhandwerk und regionale Identität. Die Brauerei setzt auf Authentizität, lokale Verbundenheit und Nachhaltigkeit. Aktuell engagiert sie sich stark in der Düsseldorfer Kulturszene, unterstützt gastronomische Traditionsbetriebe (wie die Altstadt-Kneipen) und ist präsent bei lokalen Festen wie dem Bürgerfest oder der Himmelfahrtsgala."
    ),
    "Schlenkerla Rauchbier" to BeerInfo(
        brand = "Brauerei Schlenkerla",
        detail1 = "Die Brauerei Schlenkerla aus Bamberg ist eine der traditionsreichsten und bekanntesten Brauereien der Fränkischen Schweiz. Bekannt ist sie vor allem für ihre rauchigen Biere wie das Schlenkerla Rauchbier Urbock – ein intensives, unverwechselbares Rauchbier, das mit Holzfeuer geräucherten Gerstenmalzes gebraut wird und weltweit Bierliebhaber:innen begeistert.",
        detail2 = "Seit über 600 Jahren steht die Brauerei Schlenkerla für handwerkliches Brauvermögen, regionale Identität und außergewöhnliche Geschmackserlebnisse. Die Brauerei ist eng verwurzelt in der UNESCO-Welterbe-Stadt Bamberg und verbindet Tradition mit Authentizität. Aktuell lockt das Schlenkerla Wirtshaus mit urigem Ambiente Gäste aus aller Welt an, während die Brauerei auch international durch Verkostungen und Kooperationen Aufmerksamkeit erregt. Saisonal stehen etwa das Aecht Schlenkerla Rauchbier Helles oder der Urbock im Fokus."
    )
)
