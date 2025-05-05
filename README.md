## 📄 **README.md**

# SmartCart: Mobile Shopping Smart Cart  

## 📝 Introduction
SmartCart is a mobile application designed to enhance the in-store shopping experience by allowing customers to scan product barcodes directly with their smartphone. Each scanned product is automatically added to a virtual shopping cart where users can manage product quantities, view details, and calculate the total price in real-time.

This project helps customers keep track of their purchases while shopping and simplifies the checkout process.

---

## ❓ Problem Statement
In physical retail stores, customers often struggle with:
- Keeping track of purchased items manually.
- Forgetting how many units of a product they’ve picked.
- Waiting in long checkout lines.
- Not knowing the total amount spent until checkout.

SmartCart solves these problems by enabling customers to scan product barcodes as they shop, automatically adding them to a digital cart with real-time price tracking and quantity management.

---

## 🎯 Objectives
The main goals of SmartCart are:
- ✅ Allow customers to **scan product barcodes** using their phone’s camera.
- ✅ **Automatically add products** to a virtual shopping cart.
- ✅ **Merge duplicate scans** by increasing the product quantity rather than duplicating items.
- ✅ Let users **adjust product quantity** directly in the cart.
- ✅ Display the **total price** of the cart in real-time.
- ✅ Provide an intuitive, easy-to-use user interface.
- ✅ Lay the groundwork for future payment system integration.

---

## 🏗️ Technology Stack

| Layer        | Tools / Libraries Used                                 |
|--------------|--------------------------------------------------------|
| **Frontend** | Jetpack Compose (Kotlin)                               |
| **Backend**  | Kotlin                                                 |
| **Scanner**  | Google ML Kit Barcode Scanning                         |
| **Camera**   | Android CameraX                                        |
| **Other**    | Android SDK, Kotlin Coroutines, MediaPlayer API        |
| **Database** | Firebase Realtime Database                             |
---

## 🛠️ Installation Instructions

Follow these steps to set up and run the project locally:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/smartcart.git](https://github.com/D-Arshad-Dazai-Dimash/SmartCartProject_16-P

2. **Open the project in Android Studio:**

   * Open Android Studio.
   * Click **“Open an existing project”**.
   * Navigate to the cloned `smartcart` folder.

3. **Sync Gradle:**

   * If Gradle doesn’t sync automatically, click **“Sync Project with Gradle Files”**.

4. **Add Firebase configuration:**

   * Download the **`google-services.json`** file from your Firebase project.
   * Place the file inside the **`app/`** directory.

5. **Run the application:**

   * Connect your Android device or launch an emulator.
   * Press **Run ▶️** in Android Studio.

⚠️ **Note:**

* The app requires **Camera Permission** to function.
* Make sure your Firebase Realtime Database is properly configured and active.

---

## 🕹️ Usage Guide

Here’s how to use SmartCart:

1. **Launch the app** — the barcode scanner will open.
2. **Point your phone’s camera at a product barcode** — the barcode will be scanned automatically.
3. **A beep sound will play**, and the product will be added to your cart.
4. If you scan the same product again, **the quantity will increase by 1 instead of adding a duplicate.**
5. **Click the cart button** to view the cart.
6. In the cart screen, you can:

   * See all added products with names, prices, and quantities.
   * Increase or decrease quantity using the **+** and **–** buttons.
   * Remove an item by clicking the **trash icon**.
7. **Click "Go to Payment"** to proceed (currently a placeholder screen).

✅ The total price updates automatically as you adjust quantities.

---

## 🧪 Testing

Currently, the application is tested manually by:

* Running on physical Android devices.
* Running on Android Studio emulator.
---

## ⚠️ Known Issues / Limitations

* Barcode detection may be less accurate under poor lighting conditions.
* App tested only on Android **API level 26+** (Android 8.0 and above).
* Payment gateway not implemented (navigation only).
* Requires a stable internet connection for Firebase.

---

## 📚 References

* [Firebase Realtime Database Documentation](https://smart-9f34e-default-rtdb.firebaseio.com/)
* [ML Kit Barcode Scanning Documentation](https://developers.google.com/ml-kit/vision/barcode-scanning?hl=ru)
* [CameraX Documentation](https://developer.android.com/media/camera/camerax?hl=ru)
* [Jetpack Compose Official Docs](https://developer.android.com/develop/ui/compose/documentation?hl=ru)

---

## 👥 Team Members

* Dimash Yeskendir, 220103327, 16-P
* Olzhas Musakhan, 220103033, 16-P
* Asylzhan Bitore, 220103181, 14-P
* Ayan Amantay, 123456, 16-P
* Damir Turgambekov, 220103278, 17-P

