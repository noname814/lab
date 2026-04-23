# EXAM PREP — Mobile & Web Dev (QB Q5–Q11)

> **Strategy**: Every answer = copy the code exactly. For Cordova/Ionic/RN/Flutter, examiners mainly check structure + logic. For React, just App.js matters.

---

## Q5 — Cordova: Login Screen

**Files needed**: `www/index.html` only (Cordova auto-generates the rest)

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Login</title>
  <style>
    body { font-family: sans-serif; padding: 20px; }
    input, button { display: block; width: 100%; margin: 8px 0; padding: 10px; }
    img { width: 100%; }
  </style>
</head>
<body>
  <!-- Header Image -->
  <img src="img/header.png" alt="Header">

  <!-- Label -->
  <h2>User Login</h2>

  <!-- Form using div layout manager -->
  <div id="layout">
    <label>Username</label>
    <input type="text" id="uname" placeholder="Enter username">

    <label>Password</label>
    <input type="password" id="pwd" placeholder="Enter password">

    <button onclick="reset()">Reset</button>
    <button onclick="submit()">Submit</button>
    <p id="msg"></p>
  </div>

  <script src="cordova.js"></script>
  <script>
    function reset() {
      document.getElementById('uname').value = '';
      document.getElementById('pwd').value = '';
      document.getElementById('msg').textContent = '';
    }
    function submit() {
      var u = document.getElementById('uname').value;
      var p = document.getElementById('pwd').value;
      if (u && p) document.getElementById('msg').textContent = 'Welcome, ' + u + '!';
      else document.getElementById('msg').textContent = 'Fill all fields!';
    }
  </script>
</body>
</html>
```

**Setup commands** (run once):
```bash
npm install -g cordova
cordova create myApp com.example.app MyApp
cd myApp
cordova platform add android
cordova run android
```

**Key terms to mention in viva**: `cordova.js`, `www/` folder, `config.xml`, `cordova platform add android`

---

## Q6 — Cordova: Current Location (Geolocation)

**Extra step**: Add plugin → `cordova plugin add cordova-plugin-geolocation`

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Location</title>
  <style>
    body { font-family: sans-serif; padding: 20px; text-align: center; }
    button { padding: 12px 24px; margin-top: 20px; font-size: 16px; }
    #result { margin-top: 20px; font-size: 18px; }
  </style>
</head>
<body>
  <h2>Current Location</h2>
  <button onclick="getLocation()">Get My Location</button>
  <div id="result">Press the button...</div>

  <script src="cordova.js"></script>
  <script>
    function getLocation() {
      document.getElementById('result').textContent = 'Fetching...';
      navigator.geolocation.getCurrentPosition(
        function(pos) {
          document.getElementById('result').innerHTML =
            'Latitude: ' + pos.coords.latitude + '<br>' +
            'Longitude: ' + pos.coords.longitude + '<br>' +
            'Accuracy: ' + pos.coords.accuracy + ' m';
        },
        function(err) {
          document.getElementById('result').textContent = 'Error: ' + err.message;
        }
      );
    }
  </script>
</body>
</html>
```

**Key terms**: `navigator.geolocation.getCurrentPosition()`, `coords.latitude`, `coords.longitude`, plugin

---

## Q7 — Ionic: Recipe Suggester (Ionic React)

**Setup**:
```bash
npm install -g @ionic/cli
ionic start recipeApp blank --type=react
cd recipeApp
ionic serve
```

**Replace `src/App.tsx` with:**

```tsx
import React, { useState } from 'react';
import {
  IonApp, IonHeader, IonToolbar, IonTitle, IonContent,
  IonItem, IonLabel, IonInput, IonButton, IonCard,
  IonCardHeader, IonCardTitle, IonCardContent
} from '@ionic/react';

const DB: Record<string, { nutrition: string; steps: string }> = {
  egg:    { nutrition: '155 cal, 13g protein', steps: '1. Beat eggs. 2. Fry in pan. 3. Season & serve.' },
  tomato: { nutrition: '18 cal, 0.9g protein', steps: '1. Chop tomatoes. 2. Boil with spices. 3. Blend & serve.' },
  banana: { nutrition: '89 cal, 1.1g protein', steps: '1. Peel banana. 2. Blend with milk. 3. Serve as smoothie.' },
};

const App: React.FC = () => {
  const [ing, setIng] = useState('');
  const [recipe, setRecipe] = useState<any>(null);

  const find = () => {
    const key = Object.keys(DB).find(k => ing.toLowerCase().includes(k));
    setRecipe(key
      ? { name: key.charAt(0).toUpperCase() + key.slice(1) + ' Dish', ...DB[key] }
      : { name: 'No recipe found', nutrition: '-', steps: 'Try: egg, tomato, banana' }
    );
  };

  return (
    <IonApp>
      <IonHeader>
        <IonToolbar><IonTitle>Recipe Finder</IonTitle></IonToolbar>
      </IonHeader>
      <IonContent className="ion-padding">
        <IonItem>
          <IonLabel position="floating">Enter Ingredients</IonLabel>
          <IonInput value={ing} onIonChange={e => setIng(e.detail.value!)} />
        </IonItem>
        <IonButton expand="block" onClick={find} style={{ marginTop: 12 }}>
          Find Recipe
        </IonButton>
        {recipe && (
          <IonCard>
            <IonCardHeader><IonCardTitle>{recipe.name}</IonCardTitle></IonCardHeader>
            <IonCardContent>
              <b>Nutrition:</b> {recipe.nutrition}<br /><br />
              <b>Instructions:</b><br />{recipe.steps}
            </IonCardContent>
          </IonCard>
        )}
      </IonContent>
    </IonApp>
  );
};
export default App;
```

**Key terms**: `IonApp`, `IonContent`, `IonCard`, `ionic serve`, `@ionic/react`

---

## Q8 — React Native: BMI Calculator

**Setup**:
```bash
npx create-expo-app BMIApp
cd BMIApp
npx expo start
```

**Replace `App.js` with:**

```jsx
import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';

export default function App() {
  const [height, setHeight] = useState('');
  const [weight, setWeight] = useState('');
  const [result, setResult] = useState('');

  const calcBMI = () => {
    const h = parseFloat(height);
    const w = parseFloat(weight);
    if (!h || !w) { setResult('Enter valid values'); return; }
    const bmi = (w / (h * h)).toFixed(2);
    const cat = bmi < 18.5 ? 'Underweight' : bmi < 25 ? 'Normal' : bmi < 30 ? 'Overweight' : 'Obese';
    setResult(`BMI: ${bmi} — ${cat}`);
  };

  return (
    <View style={s.container}>
      <Text style={s.title}>BMI Calculator</Text>
      <TextInput style={s.input} placeholder="Height (in meters, e.g. 1.75)"
        keyboardType="numeric" onChangeText={setHeight} />
      <TextInput style={s.input} placeholder="Weight (in kg, e.g. 70)"
        keyboardType="numeric" onChangeText={setWeight} />
      <TouchableOpacity style={s.btn} onPress={calcBMI}>
        <Text style={s.btnText}>Calculate BMI</Text>
      </TouchableOpacity>
      {result ? <Text style={s.result}>{result}</Text> : null}
    </View>
  );
}

const s = StyleSheet.create({
  container: { flex: 1, justifyContent: 'center', padding: 24, backgroundColor: '#f0f4f8' },
  title:     { fontSize: 26, fontWeight: 'bold', textAlign: 'center', marginBottom: 24 },
  input:     { borderWidth: 1, borderColor: '#aaa', borderRadius: 8, padding: 12, marginBottom: 12, backgroundColor: '#fff' },
  btn:       { backgroundColor: '#4a90e2', padding: 14, borderRadius: 8, alignItems: 'center' },
  btnText:   { color: '#fff', fontSize: 16, fontWeight: 'bold' },
  result:    { marginTop: 20, fontSize: 20, textAlign: 'center', fontWeight: 'bold' },
});
```

**Key terms**: `View`, `Text`, `TextInput`, `TouchableOpacity`, `StyleSheet`, `expo start`

---

## Q9 — Flutter: Expense Manager

**Setup**:
```bash
flutter create expense_app
cd expense_app
# Replace lib/main.dart with code below
flutter run
```

**`lib/main.dart`:**

```dart
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(home: ExpenseApp()));

class ExpenseApp extends StatefulWidget {
  @override _ExpState createState() => _ExpState();
}

class _ExpState extends State<ExpenseApp> {
  final _amt = TextEditingController();
  final _cat = TextEditingController();
  String _type = 'Expense';
  List<Map<String, dynamic>> _items = [];

  void _add() {
    if (_cat.text.isEmpty || _amt.text.isEmpty) return;
    setState(() => _items.add({
      'type': _type,
      'cat': _cat.text,
      'amt': double.tryParse(_amt.text) ?? 0,
    }));
    _cat.clear(); _amt.clear();
  }

  Map<String, double> get _summary {
    Map<String, double> s = {};
    for (var i in _items) {
      double v = i['type'] == 'Income' ? i['amt'] : -i['amt'];
      s[i['cat']] = (s[i['cat']] ?? 0) + v;
    }
    return s;
  }

  @override
  Widget build(BuildContext context) => Scaffold(
    appBar: AppBar(title: Text('Expense Manager'), backgroundColor: Colors.indigo),
    body: Padding(padding: EdgeInsets.all(16), child: Column(children: [
      TextField(controller: _cat, decoration: InputDecoration(labelText: 'Category (e.g. Food)')),
      TextField(controller: _amt, decoration: InputDecoration(labelText: 'Amount'),
          keyboardType: TextInputType.number),
      Row(children: [
        Text('Type: '),
        DropdownButton<String>(
          value: _type,
          items: ['Expense', 'Income'].map((e) =>
            DropdownMenuItem(value: e, child: Text(e))).toList(),
          onChanged: (v) => setState(() => _type = v!),
        ),
        Spacer(),
        ElevatedButton(onPressed: _add, child: Text('Add')),
      ]),
      Divider(),
      Text('Weekly Summary', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
      Expanded(child: ListView(
        children: _summary.entries.map((e) => ListTile(
          title: Text(e.key),
          trailing: Text(
            e.value.toStringAsFixed(2),
            style: TextStyle(color: e.value >= 0 ? Colors.green : Colors.red, fontWeight: FontWeight.bold),
          ),
        )).toList(),
      )),
    ])),
  );
}
```

**Key terms**: `StatefulWidget`, `setState()`, `TextEditingController`, `Scaffold`, `ListView`, `DropdownButton`

---

## Q10 — React: Unit Converter (Imperial ↔ Metric)

**Setup**:
```bash
npx create-react-app unit-converter
cd unit-converter
# Replace src/App.js with code below
npm start
```

**`src/App.js`:**

```jsx
import React, { useState } from 'react';

const conversions = [
  { label: 'Kilometers → Miles',    from: 'km',  to: 'miles',   fn: v => v * 0.621371  },
  { label: 'Miles → Kilometers',    from: 'mi',  to: 'km',      fn: v => v * 1.60934   },
  { label: 'Kilograms → Pounds',    from: 'kg',  to: 'lbs',     fn: v => v * 2.20462   },
  { label: 'Pounds → Kilograms',    from: 'lbs', to: 'kg',      fn: v => v * 0.453592  },
  { label: 'Celsius → Fahrenheit',  from: '°C',  to: '°F',      fn: v => v * 9/5 + 32  },
  { label: 'Fahrenheit → Celsius',  from: '°F',  to: '°C',      fn: v => (v - 32) * 5/9},
  { label: 'Centimeters → Inches',  from: 'cm',  to: 'in',      fn: v => v * 0.393701  },
  { label: 'Liters → Gallons',      from: 'L',   to: 'gal',     fn: v => v * 0.264172  },
];

export default function App() {
  const [val, setVal] = useState('');
  const [sel, setSel] = useState(0);
  const c = conversions[sel];
  const result = val !== '' ? c.fn(parseFloat(val)).toFixed(4) : '';

  return (
    <div style={{ maxWidth: 480, margin: '60px auto', fontFamily: 'sans-serif', padding: 24 }}>
      <h2>⚖️ Unit Converter</h2>
      <select onChange={e => { setSel(parseInt(e.target.value)); setVal(''); }} style={{ width:'100%', padding:10, marginBottom:16 }}>
        {conversions.map((c, i) => <option key={i} value={i}>{c.label}</option>)}
      </select>
      <input
        type="number"
        value={val}
        onChange={e => setVal(e.target.value)}
        placeholder={`Enter value in ${c.from}`}
        style={{ width:'100%', padding:10, marginBottom:16, boxSizing:'border-box' }}
      />
      {result && (
        <div style={{ background:'#e8f4fd', padding:16, borderRadius:8, fontSize:22, textAlign:'center' }}>
          {val} {c.from} = <b>{result} {c.to}</b>
        </div>
      )}
    </div>
  );
}
```

**Key terms**: `useState`, `onChange`, `props`, array of conversion objects, `parseFloat`

---

## Q11 — React: To-Do List App

**Setup**:
```bash
npx create-react-app todo-app
cd todo-app
# Replace src/App.js with code below
npm start
```

**`src/App.js`:**

```jsx
import React, { useState } from 'react';

export default function App() {
  const [tasks, setTasks] = useState([]);
  const [input, setInput] = useState('');
  const [filter, setFilter] = useState('All');

  const add = () => {
    if (!input.trim()) return;
    setTasks([...tasks, { text: input, done: false }]);
    setInput('');
  };

  const toggle = i => setTasks(tasks.map((t, j) => j === i ? { ...t, done: !t.done } : t));
  const del    = i => setTasks(tasks.filter((_, j) => j !== i));

  const visible = tasks.filter(t =>
    filter === 'All' ? true : filter === 'Done' ? t.done : !t.done
  );

  return (
    <div style={{ maxWidth: 500, margin: '60px auto', fontFamily: 'sans-serif', padding: 24 }}>
      <h2>📋 To-Do Manager</h2>

      {/* Input Row */}
      <div style={{ display:'flex', gap:8, marginBottom:16 }}>
        <input
          value={input}
          onChange={e => setInput(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && add()}
          placeholder="Add a new task..."
          style={{ flex:1, padding:10, fontSize:15 }}
        />
        <button onClick={add} style={{ padding:'10px 20px' }}>Add</button>
      </div>

      {/* Filter */}
      {['All','Active','Done'].map(f => (
        <button key={f} onClick={() => setFilter(f)}
          style={{ marginRight:8, fontWeight: filter===f ? 'bold':'normal' }}>
          {f}
        </button>
      ))}

      {/* Task List */}
      <ul style={{ listStyle:'none', padding:0, marginTop:16 }}>
        {visible.map((t, i) => (
          <li key={i} style={{ display:'flex', alignItems:'center', gap:10,
            padding:10, borderBottom:'1px solid #eee' }}>
            <input type="checkbox" checked={t.done} onChange={() => toggle(tasks.indexOf(t))} />
            <span style={{ flex:1, textDecoration: t.done ? 'line-through':'none', color: t.done ? '#aaa':'#000' }}>
              {t.text}
            </span>
            <button onClick={() => del(tasks.indexOf(t))} style={{ color:'red', border:'none', background:'none', cursor:'pointer', fontSize:18 }}>✕</button>
          </li>
        ))}
      </ul>

      <p style={{ color:'#888' }}>{tasks.filter(t=>!t.done).length} task(s) remaining</p>
    </div>
  );
}
```

**Key terms**: `useState`, `Array.filter`, `Array.map`, `spread operator {...t}`, `onKeyDown`

---

## ⚡ QUICK CHEAT SHEET (memorize this)

| Q  | Tech         | Key File           | Core Concept                          |
|----|--------------|--------------------|---------------------------------------|
| 5  | Cordova      | www/index.html     | Hybrid app, plain HTML+JS             |
| 6  | Cordova      | www/index.html     | `navigator.geolocation.getCurrentPosition()` |
| 7  | Ionic React  | src/App.tsx        | IonApp, IonCard, IonInput             |
| 8  | React Native | App.js             | View, Text, TextInput, StyleSheet     |
| 9  | Flutter      | lib/main.dart      | StatefulWidget, setState, ListView    |
| 10 | React        | src/App.js         | useState, array of conversions        |
| 11 | React        | src/App.js         | useState, map/filter for task list    |

## 🧠 Viva Bullets
- **Cordova**: Wraps HTML/CSS/JS as native app. `www/` = app root. `cordova.js` bridges native APIs.
- **Ionic**: UI component library for hybrid apps. Built on Cordova/Capacitor. Components: `Ion*`.
- **React Native**: JS framework, compiles to REAL native components (not WebView). `StyleSheet` ≠ CSS.
- **Flutter**: Dart language. Everything is a Widget. `StatefulWidget` = has mutable state.
- **React**: JS library for UI. `useState` = state hook. `JSX` = HTML-in-JS syntax.
