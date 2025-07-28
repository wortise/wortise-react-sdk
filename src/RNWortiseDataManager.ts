import { NativeModules } from 'react-native';
import RNWortiseUserGender from './RNWortiseUserGender';

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

  addEmail(email: string) {
    RNWortiseDataManager.addEmail(email);
  },

  setAge(age?: number) {
    RNWortiseDataManager.setAge(age);
  },

  setEmails(list?: [string]) {
    RNWortiseDataManager.setEmails(list);
  },

  setGender(gender?: RNWortiseUserGender) {
    RNWortiseDataManager.setGender(gender);
  },
};
