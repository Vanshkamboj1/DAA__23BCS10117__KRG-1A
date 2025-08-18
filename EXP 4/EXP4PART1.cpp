/*struct Node {
	int val;
	struct Node* next;
	struct Node* prev;
	Node(int x){
		val = x;
		next = NULL;
		prev = NULL;
	}
};*/

Node* insertNode(Node* head, int position, int value) {
    Node* newNode = new Node(value);
    if (position == 1) {
        newNode->next = head;
        if (head) head->prev = newNode;
        return newNode;
    }
    Node* temp = head;
    for (int i = 1; temp && i < position - 1; i++) {
        temp = temp->next;
    }
    if (!temp) return head;
    newNode->next = temp->next;
    if (temp->next) temp->next->prev = newNode;
    temp->next = newNode;
    newNode->prev = temp;
    return head;
}

Node* deleteNode(Node* head, int position) {
    if (!head) return NULL;
    if (position == 1) {
        Node* temp = head;
        head = head->next;
        if (head) head->prev = NULL;
        delete temp;
        return head;
    }
    Node* temp = head;
    for (int i = 1; temp && i < position; i++) {
        temp = temp->next;
    }
    if (!temp) return head;
    if (temp->prev) temp->prev->next = temp->next;
    if (temp->next) temp->next->prev = temp->prev;
    delete temp;
    return head;
}
