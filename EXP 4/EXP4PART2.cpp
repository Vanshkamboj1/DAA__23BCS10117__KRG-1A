class Solution {
  public:
    Node* reverse(Node* head) {
        if (!head || head->next == head) return head;
        Node* prev = NULL;
        Node* curr = head;
        Node* next = NULL;
        Node* tail = head;
        while (tail->next != head) {
            tail = tail->next;
        }
        do {
            next = curr->next;
            curr->next = prev ? prev : head;
            prev = curr;
            curr = next;
        } while (curr != head);
        head->next = prev;
        head = prev;
        return head;
    }

    Node* deleteNode(Node* head, int key) {
        if (!head) return NULL;
        Node* curr = head;
        Node* prev = NULL;
        if (head->next == head && head->data == key) {
            delete head;
            return NULL;
        }
        if (head->data == key) {
            while (curr->next != head) curr = curr->next;
            curr->next = head->next;
            Node* temp = head;
            head = head->next;
            delete temp;
            return head;
        }
        curr = head;
        do {
            prev = curr;
            curr = curr->next;
            if (curr->data == key) {
                prev->next = curr->next;
                delete curr;
                break;
            }
        } while (curr != head);
        return head;
    }
};
