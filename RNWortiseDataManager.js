import { NativeModules } from "react-native";

const { RNWortiseDataManager } = NativeModules;

export default {
  get age() {
    return RNWortiseDataManager.getAge();
  },

  get emails() {
    return RNWortiseDataManager.getEmails();
  },

  get gender() {
    return RNWortiseDataManager.getGender();
  },

  addEmail(email) {
    RNWortiseDataManager.addEmail(email);
  },

  requestAccount(onlyIfNotAvailable) {
    return RNWortiseDataManager.requestAccount(onlyIfNotAvailable ?? true);
  },

  setAge(age) {
    RNWortiseDataManager.setAge(age);
  },

  setEmails(list) {
    RNWortiseDataManager.setEmails(list);
  },

  setGender(gender) {
    RNWortiseDataManager.setGender(gender);
  },
};
